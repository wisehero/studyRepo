package wisehero.settlementsystem.settlement.service;

import java.math.BigDecimal;
import java.sql.BatchUpdateException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wisehero.settlementsystem.payment.entity.Payment;
import wisehero.settlementsystem.payment.repository.PaymentRepository;
import wisehero.settlementsystem.settlement.entity.Settlement;
import wisehero.settlementsystem.settlement.repository.SettlementRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class SettlementScheduledTasks {
	public static final String PAYMENT_COMPLETED = "paid";

	private final PaymentRepository paymentRepository;
	private final SettlementRepository settlementRepository;
	private final JdbcTemplate jdbcTemplate;

	@Scheduled(cron = "0 * * * * *")
	@SchedulerLock(name = "ScheduledTask_run")
	public void dailySettlement() {
		// 어제 날짜 가져오기
		LocalDate yesterday = LocalDate.now().minusDays(1);
		// 어제의 시작 시각 설정
		LocalDateTime startDate = yesterday.atStartOfDay();
		// 어제의 끝 시각 설정
		LocalDateTime endDate = yesterday.atTime(23, 59, 59);

		// 해당 기간 동안의 결제 내역 조회 및 집계
		Map<Long, BigDecimal> settlementMap = getSettlementMap(startDate, endDate);

		long beforeTime = System.currentTimeMillis();
		// processSettlement(settlementMap, yesterday);
		bulkProcessSettlements(settlementMap, yesterday);
		long afterTime = System.currentTimeMillis();

		long diffTime = afterTime - beforeTime;
		log.info("실행 시간(ms) : {}", diffTime);
	}

	// 일반 스트림을 사용한 코드
	private Map<Long, BigDecimal> getSettlementMap(LocalDateTime startDate, LocalDateTime endDate) {
		List<Payment> paymentList = paymentRepository.findByPaymentDateBetweenAndStatus(startDate, endDate,
			PAYMENT_COMPLETED);

		return paymentList.stream()
			.collect(Collectors.groupingBy(
				Payment::getPartnerId,
				Collectors.reducing(
					BigDecimal.ZERO,
					Payment::getPaymentAmount,
					BigDecimal::add
				)
			));
	}

	// 병렬 스트림을 사용한 코드 -> 과연 성능을 더 개선시켜줄까?
	private Map<Long, BigDecimal> getSettlementMapWihtParallelStream(LocalDateTime startDate, LocalDateTime endDate) {
		List<Payment> paymentList = paymentRepository.findByPaymentDateBetweenAndStatus(startDate, endDate,
			PAYMENT_COMPLETED);

		return paymentList.parallelStream()
			.collect(Collectors.groupingBy(
				Payment::getPartnerId,
				Collectors.reducing(
					BigDecimal.ZERO,
					Payment::getPaymentAmount,
					BigDecimal::add
				)
			));
	}

	private void processSettlement(Map<Long, BigDecimal> settlementMap, LocalDate paymentDate) {
		settlementMap.entrySet().stream()
			.forEach(entry -> {
				Settlement settlement = Settlement.create(entry.getKey(), entry.getValue(), paymentDate);
				settlementRepository.save(settlement);
			});
	}

	private void bulkProcessSettlements(Map<Long, BigDecimal> settlementMap, LocalDate paymentDate) {
		String sql = "INSERT INTO settlements(partner_id, total_amount, payment_date) VALUES (?, ?, ?)";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Long partnerId = (Long)settlementMap.keySet().toArray()[i];
				BigDecimal totalAmount = settlementMap.get(partnerId);
				ps.setLong(1, partnerId);
				ps.setBigDecimal(2, totalAmount);
				ps.setObject(3, paymentDate);
			}

			@Override
			public int getBatchSize() {
				return settlementMap.size(); // 이 배치사이즈는 실제 시스템을 운영하면서 바꿔야할 수 있다.
			}
		});
	}

	// 예외가 발생한 것을 인지하는 배치
	private void bulkProcessSettlementsFailedHandling(Map<Long, BigDecimal> settlementMap, LocalDate paymentDate) {
		String sql = "INSERT INTO settlements(partner_id, total_amount, payment_date) VALUES (?, ?, ?)";

		List<Long> failedPartnerIds = new ArrayList<>();

		try {
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Long partnerId = (Long)settlementMap.keySet().toArray()[i];
					BigDecimal totalAmount = settlementMap.get(partnerId);
					ps.setLong(1, partnerId);
					ps.setBigDecimal(2, totalAmount);
					ps.setObject(3, paymentDate);
				}

				@Override
				public int getBatchSize() {
					return settlementMap.size(); // 이 배치사이즈는 실제 시스템을 운영하면서 바꿔야할 수 있다.
				}
			});
		} catch (DataAccessException e) {
			Throwable cause = e.getCause();
			if (cause instanceof BatchUpdateException) {
				BatchUpdateException batchEx = (BatchUpdateException)cause;
				int[] updateCounts = batchEx.getUpdateCounts();

				// 실패한 인덱스만 추출하기
				int index = 0;
				for (Map.Entry<Long, BigDecimal> entry : settlementMap.entrySet()) {
					if (updateCounts[index] == Statement.EXECUTE_FAILED) {
						failedPartnerIds.add(entry.getKey());
					}
					index++;
				}
				notifyError(failedPartnerIds);
			}
			throw e;
		}
	}

	private void notifyError(List<Long> failedPartnerIds) {
		// 에러 로그 기록
		log.error("Failed to process the following partner IDs: {}", failedPartnerIds);
		// 상세한 로그 메시지 추가
		failedPartnerIds.forEach(id -> log.debug("Failed partner ID: {}", id));

		// 담당자 알림 로직, 정산 실패 케이스를 DB에 저장
	}
}


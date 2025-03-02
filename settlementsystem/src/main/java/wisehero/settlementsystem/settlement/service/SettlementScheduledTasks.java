package wisehero.settlementsystem.settlement.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

		processSettlement(settlementMap, yesterday);
	}

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

	private void processSettlement(Map<Long, BigDecimal> settlementMap, LocalDate paymentDate) {
		settlementMap.entrySet().stream()
			.forEach(entry -> {
				Settlement settlement = Settlement.create(entry.getKey(), entry.getValue(), paymentDate);
				settlementRepository.save(settlement);
			});
	}
}

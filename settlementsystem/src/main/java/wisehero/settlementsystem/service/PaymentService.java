package wisehero.settlementsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wisehero.settlementsystem.client.PaymentClient;
import wisehero.settlementsystem.entity.Payment;
import wisehero.settlementsystem.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;

	private final PaymentClient paymentClient;

	/**
	 * 모든 결제 내역 조회
	 *
	 * @return 모든 Payment 엔티티 리스트
	 */
	public List<Payment> getAllPayments() {
		return paymentRepository.findAll();
	}

	/**
	 * 새로운 결제 내역 저장
	 *
	 * @param payment 저장할 Payment 엔티티
	 * @return 저장된 Payment 엔티티
	 */
	@Transactional
	public Payment savePayment(Payment payment) {
		System.out.println(payment);
		return paymentRepository.save(payment);
	}

	@Transactional
	public void cancelPayment(String uid) {
		// 외부 API로 결제 취소 요청
		String result = paymentClient.cancelPayment(uid);

		if (result.contains("ERROR")) {
			throw new RuntimeException("Payment cancellation failed during recovery process.");
		}

		Payment payment = paymentRepository.findByImpUid(uid)
			.orElseThrow(() -> new IllegalArgumentException("Payment not found with impUid: " + uid));

		payment.setStatus("cancel");
	}

}

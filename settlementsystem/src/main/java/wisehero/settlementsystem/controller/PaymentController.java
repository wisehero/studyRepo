package wisehero.settlementsystem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wisehero.settlementsystem.controller.request.PaymentRequest;
import wisehero.settlementsystem.entity.Payment;
import wisehero.settlementsystem.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/portone")
	public ResponseEntity<String> savePortone(@RequestBody PaymentRequest request) {
		try {
			System.out.println(request);
			paymentService.savePayment(Payment.of(request));
			return ResponseEntity.ok("Payment processed successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process payment");
		}
	}

	/**
	 * 모든 결제 내역 조회
	 *
	 * @return 모든 Payment 엔티티 리스트
	 */
	@GetMapping("/list")
	public ResponseEntity<List<Payment>> getAllPayments() {
		List<Payment> payments = paymentService.getAllPayments();
		return ResponseEntity.ok(payments);
	}
}

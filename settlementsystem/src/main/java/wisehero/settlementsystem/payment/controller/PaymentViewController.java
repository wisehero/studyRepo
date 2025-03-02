package wisehero.settlementsystem.payment.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import wisehero.settlementsystem.payment.entity.Payment;
import wisehero.settlementsystem.payment.service.PaymentService;

@Controller
@RequiredArgsConstructor
public class PaymentViewController {

	private final PaymentService paymentService;

	@GetMapping("/payment")
	public String showPaymentPage() {
		return "payment";
	}

	@GetMapping("/myorder")
	public ModelAndView myOrder() {
		// PaymentService를 통해 데이터를 가져옴
		List<Payment> paymentList = paymentService.getAllPayments();

		// ModelAndView를 사용하여 데이터와 뷰를 함께 반환
		ModelAndView mav = new ModelAndView("myorder");
		mav.addObject("paymentList", paymentList);

		return mav;
	}
}

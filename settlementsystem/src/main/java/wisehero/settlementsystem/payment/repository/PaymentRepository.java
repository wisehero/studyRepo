package wisehero.settlementsystem.payment.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import wisehero.settlementsystem.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Optional<Payment> findByImpUid(String impUid);

	List<Payment> findByPaymentDateBetweenAndStatus(LocalDateTime startDate, LocalDateTime endDate, String status);
}

package wisehero.settlementsystem.settlement.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "settlements")
@Getter
@Setter
@NoArgsConstructor
public class Settlement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "partner_id")
	private Long partnerId;

	@Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
	private BigDecimal totalAmount;

	@Column(name = "status", length = 20)
	private String status = "Pending";

	@Column(name = "payment_date")
	private LocalDate paymentDate;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public static Settlement create(Long partnerId, BigDecimal totalAmount, LocalDate paymentDate) {
		Settlement settlement = new Settlement();
		settlement.setPartnerId(partnerId);
		settlement.setTotalAmount(totalAmount);
		settlement.setStatus("completed");
		settlement.setPaymentDate(paymentDate);
		settlement.setCreatedAt(LocalDateTime.now());
		settlement.setUpdatedAt(LocalDateTime.now());
		return settlement;
	}
}

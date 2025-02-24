package wisehero.settlementsystem.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wisehero.settlementsystem.controller.request.PaymentRequest;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "partner_id")
	private Long partnerId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "order_id", nullable = false)
	private Long orderId;

	@Column(name = "payment_amount", nullable = false, precision = 15, scale = 2)
	private BigDecimal paymentAmount;

	@Column(name = "payment_date", nullable = false)
	private LocalDateTime paymentDate;

	@Column(name = "imp_uid", length = 50)
	private String impUid;

	@Column(name = "payment_method", length = 50)
	private String paymentMethod;

	@Column(name = "merchant_uid", length = 50)
	private String merchantUid;

	@Column(name = "pg_provider", length = 20)
	private String pgProvider;

	@Column(name = "pg_type", length = 20)
	private String pgType;

	@Column(name = "pg_tid", length = 50)
	private String pgTid;

	@Column(name = "status", length = 20)
	private String status;

	@Column(name = "card_name", length = 20)
	private String cardName;

	@Column(name = "card_number", length = 50)
	private String cardNumber;

	@Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
		paymentDate = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public static Payment of(PaymentRequest request) {
		return Payment.builder()
			.partnerId(request.getPartnerId())
			.userId(request.getUserId())
			.orderId(request.getOrderId())
			.impUid(request.getImpUid())
			.paymentMethod(request.getPayMethod())
			.merchantUid(request.getMerchantUid())
			.paymentAmount(BigDecimal.valueOf(request.getPaidAmount()))
			.pgProvider(request.getPgProvider())
			.pgType(request.getPgType())
			.pgTid(request.getPgTid())
			.status(request.getStatus())
			.cardName(request.getCardName())
			.cardNumber(request.getCardNumber())
			.build();
	}

}

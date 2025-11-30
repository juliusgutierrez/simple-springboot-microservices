package use.gutierrez.payment.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;


@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String referenceId;
  private String paymentMethod;
  private BigDecimal amount;
  private String currency;

  private PaymentStatus status;

  private Instant createdAt;
  private Instant updatedAt;

}

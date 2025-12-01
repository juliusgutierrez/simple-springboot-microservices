package use.gutierrez.payment.events;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCompletedEvent {
  private Long paymentId;
  private String externalReference;
  private String paymentMethod;
  private BigDecimal amount;
  private String currency;
}

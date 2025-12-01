package use.gutierrez.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentCompletedEvent {
  private Long paymentId;
  private String externalReference;
  private String paymentMethod;
  private BigDecimal amount;
  private String currency;
}

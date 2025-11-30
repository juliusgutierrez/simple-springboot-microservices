package use.gutierrez.payment.dto;

import java.math.BigDecimal;

public record PaymentRequest(
    String referenceId,
    String peymentMethod,
    BigDecimal amount,
    String currency
) {
}

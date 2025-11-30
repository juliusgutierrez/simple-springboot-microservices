package use.gutierrez.payment.dto;

public record PaymentResponse(
    Long paymentId,
    String status
) {
}

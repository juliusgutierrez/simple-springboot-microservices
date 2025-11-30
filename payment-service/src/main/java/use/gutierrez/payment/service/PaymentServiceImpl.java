package use.gutierrez.payment.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import use.gutierrez.payment.domain.Payment;
import use.gutierrez.payment.domain.PaymentStatus;
import use.gutierrez.payment.repository.PaymentRepository;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;


  @Override
  public Payment createPayment(String referenceId, String paymentMethod, BigDecimal amount, String currency) {
    Payment payment = toPayment(referenceId, paymentMethod, amount, currency);

    paymentRepository.save(payment);

    //fire a payment to third party api
    boolean isPaymentSuccess = true;
    PaymentStatus status = isPaymentSuccess ? PaymentStatus.COMPLETED : PaymentStatus.FAILED;

    payment.setStatus(status);
    payment.setUpdatedAt(Instant.now());
    paymentRepository.save(payment);

    if (isPaymentSuccess) {
      //fire an event to notify
    }

    return payment;
  }

  private Payment toPayment(String referenceId, String paymentMethod, BigDecimal amount, String currency) {
    return Payment.builder()
        .referenceId(referenceId)
        .paymentMethod(paymentMethod)
        .amount(amount)
        .currency(currency)
        .status(PaymentStatus.CREATED)
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();
  }
}

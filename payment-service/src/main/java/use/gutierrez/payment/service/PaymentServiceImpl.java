package use.gutierrez.payment.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import use.gutierrez.payment.config.RabbitConfig;
import use.gutierrez.payment.domain.Payment;
import use.gutierrez.payment.domain.PaymentStatus;
import use.gutierrez.payment.events.PaymentCompletedEvent;
import use.gutierrez.payment.repository.PaymentRepository;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;
  private final RabbitTemplate rabbitTemplate;


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

    log.info("sending event now...");

    if (isPaymentSuccess) {
      //fire an event to notify
      PaymentCompletedEvent event = PaymentCompletedEvent
          .builder()
          .paymentId(payment.getId())
          .externalReference(payment.getReferenceId())
          .paymentMethod(payment.getPaymentMethod())
          .amount(payment.getAmount())
          .currency(payment.getCurrency())
          .build();

      rabbitTemplate.convertAndSend(
          RabbitConfig.EXCHANGE_NAME,
          RabbitConfig.ROUTING_KEY,
          event
      );
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

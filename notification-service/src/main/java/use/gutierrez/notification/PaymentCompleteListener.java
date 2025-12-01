package use.gutierrez.notification;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import use.gutierrez.dto.PaymentCompletedEvent;

@Slf4j
@Component
public class PaymentCompleteListener {

  @RabbitListener(queues = "payment.completed.queue")
  public void handlePaymentCompleted(PaymentCompletedEvent event) {
    // Here you would call your email provider / SES / SendGrid
    log.info("Received PaymentCompletedEvent: {}", event);

    sendEmail(event);

    // Simulate sending email
    log.info("Sending payment success email for paymentId={}", event.getPaymentId());

  }

  private void sendEmail(PaymentCompletedEvent event) {
    log.info("📨 Sending payment success email: paymentId={}, amount={}, method={}",
        event.getPaymentId(),
        event.getAmount(),
        event.getPaymentMethod()
    );

    // In real world: Call SendGrid / SES / Mailgun here
  }

}

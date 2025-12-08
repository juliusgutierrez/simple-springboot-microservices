package use.gutierrez.payment.cron;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import use.gutierrez.payment.config.RabbitConfig;
import use.gutierrez.payment.events.PaymentCompletedEvent;
import use.gutierrez.payment.outbox.OutboxEvent;
import use.gutierrez.payment.outbox.OutboxStatus;
import use.gutierrez.payment.repository.OutboxEventRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxCron {

  private final RabbitTemplate rabbitTemplate;
  private final OutboxEventRepository repository;
  private final ObjectMapper objectMapper;

  @Scheduled(fixedDelay = 5000)  // run every 5 seconds
  public void processOutbox() {

    log.info("outbox cron start");

    List<OutboxEvent> events = repository.findByStatus(OutboxStatus.PENDING);

    for (OutboxEvent event : events) {
      try {

        PaymentCompletedEvent dto =
            objectMapper.readValue(event.getPayload(), PaymentCompletedEvent.class);

        rabbitTemplate.convertAndSend(
            RabbitConfig.EXCHANGE_NAME,
            RabbitConfig.ROUTING_KEY,
            dto
        );

        event.setStatus(OutboxStatus.SENT);
        repository.save(event);

        log.info("Outbox event {} published successfully", event.getId());


      } catch (Exception ex) {
        log.error("Failed to publish outbox event {}: {}", event.getId(), ex.getMessage());
        event.setStatus(OutboxStatus.FAILED);
        repository.save(event);
      }
    }

  }

}

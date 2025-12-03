package use.gutierrez.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import use.gutierrez.payment.outbox.OutboxEvent;
import use.gutierrez.payment.outbox.OutboxStatus;
import use.gutierrez.payment.repository.OutboxEventRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxEventServiceImpl implements OutboxEventService {

  private final OutboxEventRepository repository;
  private final ObjectMapper objectMapper;
  @Override
  public void saveEvent(String eventType, String aggregateId, Object payload) {

    log.info("saving event to outbox");

    try {
      String json = objectMapper.writeValueAsString(payload);

      OutboxEvent event = OutboxEvent.builder()
          .eventType(eventType)
          .aggregateId(aggregateId)
          .payload(json)
          .status(OutboxStatus.PENDING)
          .build();

      repository.save(event);

    } catch (Exception ex) {
      throw new RuntimeException("Outbox serialization failed", ex);
    }

  }
}

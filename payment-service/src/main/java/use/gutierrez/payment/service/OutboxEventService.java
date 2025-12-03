package use.gutierrez.payment.service;

public interface OutboxEventService {

  void saveEvent(String eventType, String aggregateId, Object payload);
}

package use.gutierrez.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import use.gutierrez.payment.outbox.OutboxEvent;
import use.gutierrez.payment.outbox.OutboxStatus;

import java.util.List;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

  List<OutboxEvent> findByStatus(OutboxStatus status);

}

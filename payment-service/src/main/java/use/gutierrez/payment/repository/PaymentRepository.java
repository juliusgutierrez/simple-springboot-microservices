package use.gutierrez.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import use.gutierrez.payment.domain.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}

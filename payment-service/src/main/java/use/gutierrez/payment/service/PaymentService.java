package use.gutierrez.payment.service;

import use.gutierrez.payment.domain.Payment;

import java.math.BigDecimal;

public interface PaymentService {

  Payment createPayment(
      String referenceId,
      String peymentMethod,
      BigDecimal amount,
      String currency
  );

}

package use.gutierrez.payment.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import use.gutierrez.payment.domain.Payment;
import use.gutierrez.payment.dto.PaymentRequest;
import use.gutierrez.payment.dto.PaymentResponse;
import use.gutierrez.payment.service.PaymentService;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping
  public ResponseEntity<PaymentResponse> create(@RequestBody PaymentRequest request) {

    Payment payment = paymentService.createPayment(
            request.referenceId(),
            request.peymentMethod(),
        request.amount(),
        request.currency()
    );

    PaymentResponse response = new PaymentResponse(
        payment.getId(), payment.getStatus().toString()
    );

    return ResponseEntity.status(201).body(response);

  }


}

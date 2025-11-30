package use.gutierrez.payment.infra;

import org.springframework.stereotype.Component;

@Component("StripePaymentGateway")
public class StripePaymentAdapter implements PaymentGateway {
}

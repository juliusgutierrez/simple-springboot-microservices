package use.gutierrez.payment.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
  public static final String EXCHANGE_NAME = "payment.events";
  public static final String QUEUE_NAME = "payment.completed.queue";
  public static final String ROUTING_KEY = "payment.completed";

  @Bean
  public TopicExchange paymentExchange() {
    return new TopicExchange(EXCHANGE_NAME);
  }

  @Bean
  public Queue paymentCompletionQueue() {
    return new Queue(QUEUE_NAME, true);
  }

  @Bean
  public Binding paymentCompletedBinding(Queue paymentCompletedQueue, TopicExchange paymentExchange) {
    return BindingBuilder
        .bind(paymentCompletedQueue)
        .to(paymentExchange)
        .with(ROUTING_KEY);
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(jsonMessageConverter());
    return template;
  }


}

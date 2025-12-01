package use.gutierrez.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConsumerConfig {
  public static final String QUEUE_NAME = "payment.completed.queue";

  @Bean
  public Queue exampleQueue() {
    return new Queue(QUEUE_NAME, true);
  }

  @Bean
  public Jackson2JsonMessageConverter jacksonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

}

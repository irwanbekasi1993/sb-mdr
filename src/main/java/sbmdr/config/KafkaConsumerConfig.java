package sbmdr.config;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerConfig {

@KafkaListener(topics="sbmdr",groupId="sbmdr")
public void consumeMessage(String message){
    System.err.println(message);
}
}

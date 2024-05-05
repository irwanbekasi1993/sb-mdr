package sbmdr.config;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerConfig {
private final KafkaTemplate<String,String> kafkaTemplate;
private final String TOPIC_NAME="sbmdr";
public KafkaProducerConfig(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
}

public void sendMessage(String message){
    kafkaTemplate.send(TOPIC_NAME,message);
    
}
}

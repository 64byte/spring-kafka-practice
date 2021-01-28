package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaFailureCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class KafkaTemplateSender {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTemplateSender.class);

    private final KafkaTemplate<String, String> template;

    public KafkaTemplateSender(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void sendTemplate(String message) {

        ListenableFuture<SendResult<String, String>> future = template.send("to-topic", message);

        future.addCallback(result -> {

            if (result != null) {
                logger.info("result isn't null");
                logger.info(result.getProducerRecord().key());
            } else {
                logger.info("result is null");
            }
        }, ex -> {
            logger.error(ex.getMessage());
        });
    }

}

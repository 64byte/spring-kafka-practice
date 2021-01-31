package com.example.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {


    public static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private KafkaTemplate<String, String> template;

    @Autowired
    private KafkaTemplateSender kafkaTemplateSender;

    private final CountDownLatch latch = new CountDownLatch(2);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        this.template.send("myTopic", "foo1");
        this.template.send("topic2", "dfasfjklesjklsaf");

        for (int i=0; i<100; ++i) {
            this.template.send("topic1", "test " + i);
        }

        kafkaTemplateSender.sendTemplate("some message");
        latch.await(60, TimeUnit.SECONDS);
        logger.info("All received");
    }

    /*
        concurrency setting
     */
    @KafkaListener(topics = "topic1", groupId = "foo",
            autoStartup = "${listen.auto.start:true}", concurrency = "${listen.concurrency:3}")
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        logger.info(cr.toString());
        latch.countDown();
    }

    @KafkaListener(topics = "to-topic", groupId = "foo")
    public void listen2(ConsumerRecord<?, ?> cr) throws Exception {
        logger.info(cr.toString());
        latch.countDown();
    }

    // test commit

}

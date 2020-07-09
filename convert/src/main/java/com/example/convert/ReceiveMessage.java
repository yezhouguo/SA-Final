package com.example.convert;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.lang.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class ReceiveMessage {

    private static final boolean NON_DURABLE = false;
    private static final String MY_QUEUE_NAME = "myQueue";

    @Autowired
    TaskQueue taskQueue;

    public static void main(String[] args) {
        SpringApplication.run(ReceiveMessage.class, args);
        
    }

    // @Bean
    // public ApplicationRunner runner(RabbitTemplate template,String filepath,String filename) {
    //     return args -> {

    //             template.convertAndSend("myQueue", "Hello, world!"+filepath+filename);

    //     };
    // }

    // @Bean
    // public void send(RabbitTemplate template, String filename) {
    //     try {
    //         template.convertAndSend("myQueue", "Hello, world!");
    //         Thread.sleep(100);
    //     } catch (InterruptedException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     }
    // }

    @Bean
    public Queue myQueue() {
        return new Queue(MY_QUEUE_NAME, NON_DURABLE);

    }

    @RabbitListener(queues = MY_QUEUE_NAME)
    public void listen(String in) throws InterruptedException {
        System.out.println("VC::Message read from myQueue : " + in);
        taskQueue.add(in);
        taskQueue.runTasks();
    }

}
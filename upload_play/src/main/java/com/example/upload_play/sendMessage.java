package com.example.upload_play;

import org.springframework.amqp.core.Queue;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//@SpringBootApplication
@Component
public class sendMessage {

    private static final boolean NON_DURABLE = false;
    private static final String MY_QUEUE_NAME = "myQueue";

    // public static void main(String[] args) {
    // SpringApplication.run(sendMessage.class, args);
    // }

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

    // @RabbitListener(queues = MY_QUEUE_NAME)
    // public void listen(String in) {
    //     System.out.println("VC::Message read from myQueue : " + in);
    // }

}
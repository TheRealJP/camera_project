package be.kdg.simulator.config;


import be.kdg.simulator.messaging.messengers.QueueConsumer;
import be.kdg.simulator.messaging.messengers.QueueMessenger;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
public class QueueConfig {

    @Bean
    public Queue queue() {
        return new Queue("camera-queue");
    }

//    @Profile("receiver")
//    @Bean
//    public QueueConsumer queueConsumer (){
//        return new QueueConsumer();
//    }
//
//    @Bean
//    public QueueMessenger queueMessenger (){
//        return new QueueMessenger();
//    }


}

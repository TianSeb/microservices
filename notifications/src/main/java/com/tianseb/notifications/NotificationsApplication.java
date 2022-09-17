package com.tianseb.notifications;

import com.tianseb.amqp.RabbitMQMessageProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
        scanBasePackages = {
                "com.tianseb.amqp",
                "com.tianseb.notifications"
        }
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.tianseb.clients"
)
public class NotificationsApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationsApplication.class,args);
    }

/*    @Bean
    CommandLineRunner commandLineRunner(RabbitMQMessageProducer producer, NotificationConfig notificationConfig) {
        return args -> {
            producer.publish(new Person("Marshanto",36)
                    ,notificationConfig.getInternalExchange()
                    ,notificationConfig.getInternalNotificationRoutingKey());
        };
    }
    record Person(String name, int age){};*/
}

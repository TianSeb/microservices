package com.tianseb.customer;

import com.tianseb.amqp.RabbitMQMessageProducer;
import com.tianseb.clients.fraud.FraudCheckResponse;
import com.tianseb.clients.fraud.FraudClient;
import com.tianseb.clients.notification.NotificationClient;
import com.tianseb.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;
@Service
public record CustomerService(CustomerRepository customerRepository
        ,FraudClient fraudClient
        ,RabbitMQMessageProducer rabbitMQMessageProducer) {
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo: check valid email
        customerRepository.saveAndFlush(customer);
        // todo: check email not taken
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if(fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        // todo: make it async. i.e add to queue
        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to the Jungle...",
                        customer.getFirstName())
        );
        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
                );
    }
}

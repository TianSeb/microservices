package com.tianseb.notifications;

import com.tianseb.clients.notification.NotificationClient;
import com.tianseb.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public record NotificationService(NotificationRepository notificationRepository,
                                  NotificationClient notificationClient) {

    public static final String SENDER = "Tianseb";

    public void sendNotificationToCustomer(NotificationRequest notificationRequest){
        notificationRepository.save(
                Notification.builder()
                    .toCustomerId(notificationRequest.toCustomerId())
                        .toCustomerEmail(notificationRequest.toCustomerEmail())
                        .sender(SENDER)
                        .message(notificationRequest.message())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
    }
}

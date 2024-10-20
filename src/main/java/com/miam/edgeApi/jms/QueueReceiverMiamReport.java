package com.miam.edgeApi.jms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class QueueReceiverMiamReport {

    @Value("${application.messaging.input-report-queue}")
    private String queueName;

    @JmsListener(destination = "${application.messaging.input-report-queue}", containerFactory = "queueListenerContainerFactoryWithDelay")
    public void functionMiamReceiver(String message) {

        System.out.println("Received message from queue [ " + queueName + " ] -> Message [ " + message + " ].");
    }

}

package me.liheng.local;

import com.solace.messaging.MessagingService;
import com.solace.messaging.config.AuthenticationStrategy;
import com.solace.messaging.config.profile.ConfigurationProfile;
import com.solace.messaging.receiver.DirectMessageReceiver;
import com.solace.messaging.receiver.MessageReceiver;
import com.solace.messaging.resources.TopicSubscription;

import java.util.concurrent.Executors;

public class UnsubAsyncDirectReceiver {
    private static int msgCount = 0;

    public static void main(String[] args) throws InterruptedException {

        final MessagingService messagingService = MessagingService.builder(ConfigurationProfile.V1)
                .fromProperties(Util.getProperties())
                .withAuthenticationStrategy(AuthenticationStrategy.BasicUserNamePassword.of(Util.userName, Util.password))
                .build().connect();  // blocking connect to the broker

        final DirectMessageReceiver receiver = messagingService.createDirectMessageReceiverBuilder().build().start();

        final MessageReceiver.MessageHandler messageHandler = (inboundMessage) -> {
            // print first
            System.out.printf("*** Received message on thread: %s : %s %n",
                    Thread.currentThread().getId(),
                    inboundMessage.getPayloadAsString());
            msgCount ++;
            if (msgCount > 8) {
                // Remove TopicSubscription's
                System.out.println("Removing TopicSubscriptions...");
                for (int i = 0; i < Util.topicStrings.length; i++) {
                    try {
                        receiver.removeSubscription(TopicSubscription.of(Util.topicStrings[i]));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // Add TopicSubscription's
        for (int i = 0; i < Util.topicStrings.length; i++) {
            receiver.addSubscription(TopicSubscription.of(Util.topicStrings[i]));
        }

        System.out.println("Thread cout: " + Thread.activeCount());
        System.out.println("Listening...");
        receiver.receiveAsync(messageHandler, Executors.newFixedThreadPool(3));

        while (true) {
            //no-op
        }
    }
}

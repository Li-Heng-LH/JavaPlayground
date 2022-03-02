package me.liheng.local;

import com.solace.messaging.MessagingService;
import com.solace.messaging.config.AuthenticationStrategy;
import com.solace.messaging.config.profile.ConfigurationProfile;
import com.solace.messaging.receiver.DirectMessageReceiver;
import com.solace.messaging.receiver.MessageReceiver;
import com.solace.messaging.resources.TopicSubscription;

import java.util.concurrent.Executors;

public class AsyncDirectReceiver {

    public static void main(String[] args) {

        System.out.println( "Async Direct Receiver!" );

        final MessagingService messagingService = MessagingService.builder(ConfigurationProfile.V1)
                .fromProperties(Util.getProperties())
                .withAuthenticationStrategy(AuthenticationStrategy.BasicUserNamePassword.of(Util.userName, Util.password))
                .build().connect();  // blocking connect to the broker

        TopicSubscription topicSubscription = TopicSubscription.of("topic/one");

        final DirectMessageReceiver receiver = messagingService.createDirectMessageReceiverBuilder()
                .withSubscriptions(topicSubscription).build().start();

        final MessageReceiver.MessageHandler messageHandler = (inboundMessage) -> {
            // print first
            System.out.printf("*** Received message on thread: %s : %s %n",
                    Thread.currentThread().getId(),
                    inboundMessage.getPayloadAsString());
            try {
                // simulate blocking processing
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // after blocking processing
            System.out.println("*** Finished with: " + inboundMessage.getPayloadAsString() + "***");
        };

        System.out.println("Listening...");
        receiver.receiveAsync(messageHandler, Executors.newFixedThreadPool(3));

        while (true) {
            //no-op
        }
    }
}

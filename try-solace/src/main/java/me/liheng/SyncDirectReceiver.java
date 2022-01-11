package me.liheng;

import com.solace.messaging.MessagingService;
import com.solace.messaging.config.AuthenticationStrategy;
import com.solace.messaging.config.profile.ConfigurationProfile;
import com.solace.messaging.receiver.DirectMessageReceiver;
import com.solace.messaging.receiver.InboundMessage;
import com.solace.messaging.resources.TopicSubscription;

public class SyncDirectReceiver {

    public static void main(String[] args) throws InterruptedException {

        System.out.println( "Sync Direct Receiver!" );

        final MessagingService messagingService = MessagingService.builder(ConfigurationProfile.V1)
                .fromProperties(Util.getProperties())
                .withAuthenticationStrategy(AuthenticationStrategy.BasicUserNamePassword.of("client", "client"))
                .build().connect();  // blocking connect to the broker

        TopicSubscription topicSubscription = TopicSubscription.of("topic/one");

        final DirectMessageReceiver receiver = messagingService.createDirectMessageReceiverBuilder()
                .withSubscriptions(topicSubscription).build().start();

        while (true) {
            System.out.println("Listening...");
            InboundMessage inboundMessage = receiver.receiveMessage();
            System.out.printf("*** Received message on thread: %s : %s %n",
                    Thread.currentThread().getId(),
                    inboundMessage.getPayloadAsString());
            Thread.sleep(10000); // simulate blocking processing
            System.out.println("*** Finished with: " + inboundMessage.getPayloadAsString() + "***");
        }

    }
}

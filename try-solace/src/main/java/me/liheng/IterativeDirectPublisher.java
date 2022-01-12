package me.liheng;

import com.solace.messaging.MessagingService;
import com.solace.messaging.config.AuthenticationStrategy;
import com.solace.messaging.config.profile.ConfigurationProfile;
import com.solace.messaging.publisher.DirectMessagePublisher;
import com.solace.messaging.publisher.OutboundMessage;
import com.solace.messaging.publisher.OutboundMessageBuilder;
import com.solace.messaging.resources.Topic;

public class IterativeDirectPublisher {

    public static void main(String[] args) throws InterruptedException {

        final MessagingService messagingService = MessagingService.builder(ConfigurationProfile.V1)
                .fromProperties(Util.getProperties())
                .withAuthenticationStrategy(AuthenticationStrategy.BasicUserNamePassword.of(Util.userName, Util.password))
                .build().connect();  // blocking connect to the broker

        final DirectMessagePublisher publisher = messagingService.createDirectMessagePublisherBuilder()
                .onBackPressureWait(1).build().start();

        OutboundMessageBuilder messageBuilder = messagingService.messageBuilder();

        for (int i = 0; i < 10000; i++) {
            String payload = "Message " + i;
            OutboundMessage message = messageBuilder.build(payload);
            System.out.println("Sending payload: " + payload + " to topic: " + Util.topicStrings[i%5]);
            publisher.publish(message, Topic.of(Util.topicStrings[i%5]));
            Thread.sleep(2000);
        }
    }
}

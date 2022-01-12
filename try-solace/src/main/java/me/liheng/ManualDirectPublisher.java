package me.liheng;

import com.solace.messaging.MessagingService;
import com.solace.messaging.config.AuthenticationStrategy;
import com.solace.messaging.config.profile.ConfigurationProfile;
import com.solace.messaging.publisher.DirectMessagePublisher;
import com.solace.messaging.publisher.OutboundMessage;
import com.solace.messaging.publisher.OutboundMessageBuilder;
import com.solace.messaging.resources.Topic;

import java.util.Scanner;

public class ManualDirectPublisher {

    public static void main(String[] args) {

        System.out.println( "Manual Direct Publisher!" );

        final MessagingService messagingService = MessagingService.builder(ConfigurationProfile.V1)
                .fromProperties(Util.getProperties())
                .withAuthenticationStrategy(AuthenticationStrategy.BasicUserNamePassword.of(Util.userName, Util.password))
                .build().connect();  // blocking connect to the broker

        final DirectMessagePublisher publisher = messagingService.createDirectMessagePublisherBuilder()
                .onBackPressureWait(1).build().start();

        OutboundMessageBuilder messageBuilder = messagingService.messageBuilder();

        String topicString = "topic/one";

        Scanner sc = new Scanner(System.in);
        System.out.println("Type messages to be sent: ");
        while (sc.hasNext()) {
            OutboundMessage message = messageBuilder.build(sc.nextLine());
            System.out.printf(">> Calling send() on %s%n", topicString);
            publisher.publish(message, Topic.of(topicString));
        }
    }
}

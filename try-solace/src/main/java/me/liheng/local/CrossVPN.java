package me.liheng.local;

import com.solace.messaging.MessagingService;
import com.solace.messaging.config.AuthenticationStrategy;
import com.solace.messaging.config.profile.ConfigurationProfile;
import com.solace.messaging.publisher.DirectMessagePublisher;
import com.solace.messaging.receiver.DirectMessageReceiver;
import com.solace.messaging.resources.Topic;
import com.solace.messaging.resources.TopicSubscription;

// Note: bridge topic is "cross/one"
public class CrossVPN {

    private static final String TOPIC = "cross/one";

    public static void main(String[] args) throws InterruptedException {

        final MessagingService messagingService1 = MessagingService.builder(ConfigurationProfile.V1)
                .fromProperties(Util.getProperties())
                .withAuthenticationStrategy(AuthenticationStrategy.BasicUserNamePassword.of(Util.userName, Util.password))
                .build().connect();

        final MessagingService messagingService2 = MessagingService.builder(ConfigurationProfile.V1)
                .fromProperties(Util.getSecondProperties())
                .withAuthenticationStrategy(AuthenticationStrategy.BasicUserNamePassword.of("admin", "admin"))
                .build().connect();

        final DirectMessagePublisher publisher = messagingService1.createDirectMessagePublisherBuilder()
                .onBackPressureWait(1).build().start();

        final TopicSubscription topicSubscription = TopicSubscription.of(TOPIC);

        final DirectMessageReceiver receiver1 = messagingService1.createDirectMessageReceiverBuilder()
                .withSubscriptions(topicSubscription).build().start();
        receiver1.receiveAsync(inboundMessage ->
                System.out.println(">>> First Received: " + inboundMessage.getPayloadAsString()));

        final DirectMessageReceiver receiver2 = messagingService2.createDirectMessageReceiverBuilder()
                .withSubscriptions(topicSubscription).build().start();
        receiver2.receiveAsync(inboundMessage ->
                System.out.println(">>> Second Received: " + inboundMessage.getPayloadAsString()));

        publisher.publish("Msg 1", Topic.of(TOPIC));
        publisher.publish("Msg 2", Topic.of(TOPIC));
        publisher.publish("Msg 3", Topic.of(TOPIC));

        Thread.sleep(2000);
        publisher.terminate(500);
        receiver1.terminate(500);
        receiver2.terminate(500);
        messagingService1.disconnect();
        messagingService2.disconnect();
    }
}

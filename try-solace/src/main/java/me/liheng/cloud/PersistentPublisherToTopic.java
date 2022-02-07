package me.liheng.cloud;

import com.solace.messaging.publisher.PersistentMessagePublisher;
import com.solace.messaging.receiver.DirectMessageReceiver;
import com.solace.messaging.receiver.MessageReceiver;
import com.solace.messaging.resources.Topic;
import com.solace.messaging.resources.TopicSubscription;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PersistentPublisherToTopic {

    public static void main(String[] args) throws IOException, InterruptedException {

        PersistentMessagePublisher persistentPublisher = CloudUtil.getMessagingService()
                .createPersistentMessagePublisherBuilder()
                .build().start();
        DirectMessageReceiver directReceiver = CloudUtil.getMessagingService().
                createDirectMessageReceiverBuilder()
                .withSubscriptions(TopicSubscription.of("topic/one"))
                .build().start();

        PersistentMessagePublisher.MessagePublishReceiptListener deliveryConfirmationListener = (publishReceipt) -> {
            System.out.println("Received receipt");
            System.out.println(publishReceipt.isPersisted());
        };

        MessageReceiver.MessageHandler messageHandler = (inboundMessage) ->
                System.out.printf("< Received message on thread: %s from %s: %s %n",
                        Thread.currentThread().getId(),
                        inboundMessage.getDestinationName(),
                        inboundMessage.getPayloadAsString());

        persistentPublisher.setMessagePublishReceiptListener(deliveryConfirmationListener);

        persistentPublisher.publish("Msg 1".getBytes(StandardCharsets.US_ASCII),
                Topic.of("topic/one"));
        System.out.println("> Published to Msg 1 to topic/one");

        directReceiver.receiveAsync(messageHandler);

        Thread.sleep(2000);
        persistentPublisher.terminate(500);
        directReceiver.terminate(500);
        CloudUtil.getMessagingService().disconnect();

    }
}

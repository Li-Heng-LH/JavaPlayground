package me.liheng.cloud;

import com.solace.messaging.publisher.PersistentMessagePublisher;
import com.solace.messaging.receiver.MessageReceiver;
import com.solace.messaging.receiver.PersistentMessageReceiver;
import com.solace.messaging.resources.Queue;
import com.solace.messaging.resources.Topic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TwoSameQueueReceiver {

    // Queues set up in Broker:
    // persistent/one -> queue1
    //                -> queue2
    // persistent/two -> queue2

    public static void main(String[] args) throws IOException, InterruptedException {

        final PersistentMessagePublisher persistentPublisher = CloudUtil.getMessagingService()
                .createPersistentMessagePublisherBuilder()
                .build().start();
        final PersistentMessagePublisher.MessagePublishReceiptListener deliveryConfirmationListener = (publishReceipt) -> {
            System.out.println("> Publisher received receipt");
        };
        persistentPublisher.setMessagePublishReceiptListener(deliveryConfirmationListener);
        final PersistentMessageReceiver receiver1 = CloudUtil.getMessagingService()
                .createPersistentMessageReceiverBuilder()
                .withMessageAutoAcknowledgement()
                .build(Queue.durableExclusiveQueue("queue2")).start();
        final PersistentMessageReceiver receiver2 = CloudUtil.getMessagingService()
                .createPersistentMessageReceiverBuilder()
                .withMessageAutoAcknowledgement()
                .build(Queue.durableExclusiveQueue("queue2")).start();
        final MessageReceiver.MessageHandler messageHandler = (inboundMessage) ->
                System.out.printf("< Received message on thread: %s from %s: %s %n",
                        Thread.currentThread().getId(),
                        inboundMessage.getDestinationName(),
                        inboundMessage.getPayloadAsString());
        receiver1.receiveAsync(messageHandler);
        receiver2.receiveAsync(messageHandler);
        persistentPublisher.publish("Msg 1".getBytes(StandardCharsets.US_ASCII),
                Topic.of("persistent/two"));

        Thread.sleep(3000);
        persistentPublisher.terminate(500);
        receiver1.terminate(500);
        receiver2.terminate(500);
        CloudUtil.getMessagingService().disconnect();

        // Message is only consumed once!
    }
}

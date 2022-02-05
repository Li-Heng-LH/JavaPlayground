package me.liheng.cloud;

import com.solace.messaging.publisher.DirectMessagePublisher;
import com.solace.messaging.receiver.DirectMessageReceiver;
import com.solace.messaging.receiver.MessageReceiver;
import com.solace.messaging.receiver.PersistentMessageReceiver;
import com.solace.messaging.resources.Queue;
import com.solace.messaging.resources.Topic;
import com.solace.messaging.resources.TopicSubscription;

import java.io.IOException;

public class DirectToQueueDemo {

    // Queues set up in Broker:
    // persistent/one -> queue1
    //                -> queue2
    // persistent/two -> queue2

    public static void main(String[] args) throws IOException, InterruptedException {

        final DirectMessagePublisher directPublisher = CloudUtil.getMessagingService()
                .createDirectMessagePublisherBuilder().onBackPressureWait(1).build().start();
        final PersistentMessageReceiver queueReceiver = CloudUtil.getMessagingService()
                .createPersistentMessageReceiverBuilder()
                .withMessageAutoAcknowledgement()
                .build(Queue.durableExclusiveQueue("queue2")).start();
        final DirectMessageReceiver directReceiver = CloudUtil.getMessagingService().
                createDirectMessageReceiverBuilder()
                .withSubscriptions(TopicSubscription.of("persistent/two"))
                .build().start();
        final MessageReceiver.MessageHandler messageHandler = (inboundMessage) ->
                System.out.printf("< Received message on thread: %s from %s: %s %n",
                        Thread.currentThread().getId(),
                        inboundMessage.getDestinationName(),
                        inboundMessage.getPayloadAsString());


        directPublisher.publish("Msg 1", Topic.of("persistent/two"));
        System.out.println("> Published 'Msg 1' to persistent/two");
        queueReceiver.receiveAsync(messageHandler);
        directReceiver.receiveAsync(messageHandler);


        Thread.sleep(1000);
        directPublisher.terminate(500);
        queueReceiver.terminate(500);
        directReceiver.terminate(500);
        CloudUtil.getMessagingService().disconnect();

        //TODO: test if can send to queue directly?
    }
}

package me.liheng.cloud;

import com.solace.messaging.publisher.DirectMessagePublisher;
import com.solace.messaging.publisher.OutboundMessageBuilder;
import com.solace.messaging.receiver.MessageReceiver;
import com.solace.messaging.receiver.PersistentMessageReceiver;
import com.solace.messaging.resources.Queue;
import com.solace.messaging.resources.Topic;

import java.io.IOException;

public class DirectToQueueDemo {

    // Queues set up in Broker:
    // persistent/one -> queue1
    //                -> queue2
    // persistent/two -> queue2

    public static void main(String[] args) throws IOException, InterruptedException {

        final DirectMessagePublisher directPublisher = CloudUtil.getMessagingService()
                .createDirectMessagePublisherBuilder().onBackPressureWait(1).build().start();
        //OutboundMessageBuilder messageBuilder = CloudUtil.getMessagingService().messageBuilder();
        final PersistentMessageReceiver queueReceiver = CloudUtil.getMessagingService()
                .createPersistentMessageReceiverBuilder()
                .withMessageAutoAcknowledgement()
                .build(Queue.durableExclusiveQueue("queue2")).start();
        final MessageReceiver.MessageHandler messageHandler = (inboundMessage) ->
                System.out.printf("< Received message on thread: %s : %s %n",
                        Thread.currentThread().getId(), inboundMessage.getPayloadAsString());

        directPublisher.publish("Msg 1", Topic.of("persistent/two"));
        System.out.println("> Published 'Msg 1' to persistent/two");
        queueReceiver.receiveAsync(messageHandler);
        Thread.sleep(1000);

        directPublisher.terminate(500);
        queueReceiver.terminate(500);
        CloudUtil.getMessagingService().disconnect();
    }
}

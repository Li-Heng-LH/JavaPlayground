package me.liheng.cloud;

import com.solace.messaging.publisher.DirectMessagePublisher;
import com.solace.messaging.receiver.MessageReceiver;
import com.solace.messaging.receiver.PersistentMessageReceiver;
import com.solace.messaging.resources.Queue;
import com.solace.messaging.resources.Topic;

import java.io.IOException;

public class PersistentReceiverOnTopic {

    public static void main(String[] args) throws IOException, InterruptedException {


        final DirectMessagePublisher directPublisher = CloudUtil.getMessagingService()
                .createDirectMessagePublisherBuilder().onBackPressureWait(1).build().start();

        final PersistentMessageReceiver queueReceiver = CloudUtil.getMessagingService()
                .createPersistentMessageReceiverBuilder()
                .withMessageAutoAcknowledgement()
                .build(Queue.durableExclusiveQueue("queue2")).start();

        final MessageReceiver.MessageHandler messageHandler = (inboundMessage) ->
                System.out.printf("< Received message on thread: %s from %s: %s %n",
                        Thread.currentThread().getId(),
                        inboundMessage.getDestinationName(),
                        inboundMessage.getPayloadAsString());

        queueReceiver.receiveAsync(messageHandler);


        /*
         Permission Not Allowed - Queue 'queue2' - Topic 'topic/two'
         */
        //queueReceiver.addSubscription(TopicSubscription.of("topic/two"));


        directPublisher.publish("Msg 1", Topic.of("persistent/two"));
        System.out.println("> Published 'Msg 1' to persistent/two");
        directPublisher.publish("Msg 2", Topic.of("topic/two"));
        System.out.println("> Published 'Msg 2' to topic/two");

        Thread.sleep(2000);
        directPublisher.terminate(500);
        queueReceiver.terminate(500);
        CloudUtil.getMessagingService().disconnect();

    }
}

package me.liheng.cloud;

import com.solace.messaging.publisher.DirectMessagePublisher;
import com.solace.messaging.receiver.DirectMessageReceiver;
import com.solace.messaging.receiver.MessageReceiver;
import com.solace.messaging.resources.Topic;
import com.solace.messaging.resources.TopicSubscription;

import java.io.IOException;

public class RemovingNonExistingTopic {

    public static void main(String[] args) throws IOException, InterruptedException {

        final DirectMessagePublisher directPublisher = CloudUtil.getMessagingService()
                .createDirectMessagePublisherBuilder().onBackPressureWait(1).build().start();
        final DirectMessageReceiver directReceiver = CloudUtil.getMessagingService().
                createDirectMessageReceiverBuilder()
                .build().start();
        final MessageReceiver.MessageHandler messageHandler = (inboundMessage) ->
                System.out.printf("< Received message on thread: %s from %s: %s %n",
                        Thread.currentThread().getId(),
                        inboundMessage.getDestinationName(),
                        inboundMessage.getPayloadAsString());

        directReceiver.addSubscription(TopicSubscription.of("topic/one"));
        directReceiver.addSubscription(TopicSubscription.of("topic/two"));
        directReceiver.receiveAsync(messageHandler);
        directPublisher.publish("Msg 1", Topic.of("topic/one"));
        directPublisher.publish("Msg 2", Topic.of("topic/two"));
        directReceiver.removeSubscription(TopicSubscription.of("NOT/THERE"));  //This is fine

        Thread.sleep(1000);
        directPublisher.terminate(500);
        directReceiver.terminate(500);
        CloudUtil.getMessagingService().disconnect();
    }
}

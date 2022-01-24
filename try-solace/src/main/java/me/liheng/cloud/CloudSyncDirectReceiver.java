package me.liheng.cloud;

import com.solace.messaging.receiver.DirectMessageReceiver;
import com.solace.messaging.resources.TopicSubscription;
import me.liheng.Util;

import java.io.IOException;

public class CloudSyncDirectReceiver {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("main Thread: " + Thread.currentThread().getId());

        final DirectMessageReceiver receiver = CloudUtil.getMessagingService()
                .createDirectMessageReceiverBuilder()
                .build()
                .start();

        addSubscriptionsAsync(receiver);

        System.out.println("Active threads count: " + Thread.activeCount());

        while (true) {
            //no-op
        }
    }

    private static void addSubscriptions(DirectMessageReceiver receiver) throws InterruptedException {
        for (int i = 0; i < Util.topicStrings.length; i++) {
            receiver.addSubscription(TopicSubscription.of(Util.topicStrings[i]));
        }
    }

    private static void addSubscriptionsAsync(DirectMessageReceiver receiver) {
        for (int i = 0; i < Util.topicStrings.length; i++) {
            receiver.addSubscriptionAsync(
                    TopicSubscription.of(Util.topicStrings[i]),
                    (topic, op, e) -> System.out.println(op + ": " + topic + " on Thread: " + Thread.currentThread().getId())
            );
        }
    }
}

package me.liheng.cloud;

import com.solace.messaging.receiver.DirectMessageReceiver;
import com.solace.messaging.receiver.MessageReceiver;
import com.solace.messaging.resources.TopicSubscription;
import me.liheng.local.Util;

import java.io.IOException;
import java.util.concurrent.Executors;

public class CloudDirectAsyncReceiver {

    public static void run() throws IOException, InterruptedException {
        final DirectMessageReceiver receiver = CloudUtil.getMessagingService().createDirectMessageReceiverBuilder().build().start();
        for (int i = 0; i < Util.topicStrings.length; i++) {
            receiver.addSubscription(TopicSubscription.of(Util.topicStrings[i])); //sync add subscriptions
        }

        final MessageReceiver.MessageHandler messageHandler = (inboundMessage) ->
                System.out.printf("< Received message on thread: %s : %s %n",
                        Thread.currentThread().getId(), inboundMessage.getPayloadAsString());

        System.out.println("< Receiver listening...");
        receiver.receiveAsync(messageHandler, Executors.newFixedThreadPool(3));

        while (true) {
            //no-op
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        run();
    }
}

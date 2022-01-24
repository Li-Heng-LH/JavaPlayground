package me.liheng.cloud;

import com.solace.messaging.publisher.DirectMessagePublisher;
import com.solace.messaging.publisher.OutboundMessage;
import com.solace.messaging.publisher.OutboundMessageBuilder;
import com.solace.messaging.resources.Topic;
import me.liheng.Util;

import java.io.IOException;

public class CloudIterativeDirectPublisher {

    public static void run () {
        try {
            final DirectMessagePublisher publisher = CloudUtil.getMessagingService()
                    .createDirectMessagePublisherBuilder().onBackPressureWait(1).build().start();

            OutboundMessageBuilder messageBuilder = CloudUtil.getMessagingService()
                    .messageBuilder();

            System.out.println("> Publisher sending...");
            for (int i = 0; i < 10000; i++) {
                String payload = "Message " + i;
                OutboundMessage message = messageBuilder.build(payload);
                System.out.println("> Sending payload: " + payload + " to topic: " + Util.topicStrings[i%5]);
                publisher.publish(message, Topic.of(Util.topicStrings[i%5]));
                Thread.sleep(2000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

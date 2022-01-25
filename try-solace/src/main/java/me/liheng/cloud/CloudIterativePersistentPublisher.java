package me.liheng.cloud;

import com.solace.messaging.publisher.PersistentMessagePublisher;
import com.solace.messaging.resources.Topic;
import me.liheng.Util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CloudIterativePersistentPublisher {

    public static void run () {

        try {
            final PersistentMessagePublisher publisher = CloudUtil.getMessagingService()
                    .createPersistentMessagePublisherBuilder()
                    .build().start();

            // listener that processes all delivery confirmations/timeouts for all messages all
            // messages being send using given instance of messagePublisher
            final PersistentMessagePublisher.MessagePublishReceiptListener deliveryConfirmationListener = (publishReceipt) -> {
                System.out.println("Published");
            };

            // listen to all delivery confirmations for all messages being send
            publisher.setMessagePublishReceiptListener(deliveryConfirmationListener);

            // publishing a message (raw byte [] payload in this case)
            System.out.println("> Publisher sending...");
            for (int i = 0; i < 10000; i++) {
                String payload = "Message " + i;
                System.out.println("> Sending payload: " + payload + " to topic: " + Util.persistentTopics[i%2]);
                publisher.publish(payload.getBytes(StandardCharsets.US_ASCII),
                        Topic.of(Util.persistentTopics[i%2]));
                Thread.sleep(2000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

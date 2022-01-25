package me.liheng.cloud;

import com.solace.messaging.receiver.InboundMessage;
import com.solace.messaging.receiver.PersistentMessageReceiver;
import com.solace.messaging.resources.Queue;

import java.io.IOException;

public class QueueReceiver {

    private String queueName;

    public QueueReceiver(String queueName) {
        this.queueName = queueName;
    }

    public void run() {
        try {
            // Receives from a Queue
            // Yes, need to set up queue with queueName in Broker
            final PersistentMessageReceiver receiver = CloudUtil.getMessagingService()
                    .createPersistentMessageReceiverBuilder()
                    .build(Queue.durableExclusiveQueue(queueName)).start(); //Need to set up
            System.out.println("< " + queueName + " Receiver listening...");

            while (true) {
                InboundMessage message = receiver.receiveMessage(); //This is a BLOCKING call
                System.out.println("< " + queueName + " Receiver Received message: " + message.getPayloadAsString());
                receiver.ack(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

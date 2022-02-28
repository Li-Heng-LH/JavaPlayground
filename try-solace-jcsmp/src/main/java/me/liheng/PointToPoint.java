package me.liheng;

import com.solacesystems.jcsmp.*;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class PointToPoint {

    // Point -> queue2 -> Point
    public static void main(String[] args) throws IOException, JCSMPException, InterruptedException {

        final JCSMPSession session = Util.getSession();

        // create the queue object locally
        String queueName = "queue2";
        final Queue queue = JCSMPFactory.onlyInstance().createQueue(queueName);
        // set queue permissions to "consume" and access-type to "exclusive"
        final EndpointProperties endpointProps = new EndpointProperties();
        endpointProps.setPermission(EndpointProperties.PERMISSION_CONSUME);
        endpointProps.setAccessType(EndpointProperties.ACCESSTYPE_EXCLUSIVE);
        // Actually provision it, and do not fail if it already exists
        session.provision(queue, endpointProps, JCSMPSession.FLAG_IGNORE_ALREADY_EXISTS);


        final ConsumerFlowProperties flow_prop = new ConsumerFlowProperties();
        flow_prop.setEndpoint(queue);
        flow_prop.setAckMode(JCSMPProperties.SUPPORTED_MESSAGE_ACK_CLIENT);
        EndpointProperties endpoint_props = new EndpointProperties();
        endpoint_props.setAccessType(EndpointProperties.ACCESSTYPE_EXCLUSIVE);
        final FlowReceiver cons = session.createFlow(new XMLMessageListener() {
            @Override
            public void onReceive(BytesXMLMessage msg) {
                if (msg instanceof TextMessage) {
                    System.out.printf("TextMessage received: '%s'%n", ((TextMessage) msg).getText());
                } else {
                    System.out.println("Message received.");
                }
                System.out.printf("Message Dump:%n%s%n", msg.dump());
                // When the ack mode is set to SUPPORTED_MESSAGE_ACK_CLIENT,
                // guaranteed delivery messages are acknowledged after
                // processing
                msg.ackMessage();
            }
            @Override
            public void onException(JCSMPException e) {
                System.out.printf("Consumer received exception: %s%n", e);
            }
        }, flow_prop, endpoint_props);
        cons.start();


        XMLMessageProducer prod = session.getMessageProducer(new JCSMPStreamingPublishEventHandler() {
            @Override
            public void responseReceived(String messageID) {
                System.out.println("Producer received response for msg: " + messageID);
            }
            @Override
            public void handleError(String messageID, JCSMPException e, long timestamp) {
                System.out.printf("Producer received error for msg: %s@%s - %s%n",messageID,timestamp,e);
            }
        });
        TextMessage msg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
        msg.setDeliveryMode(DeliveryMode.PERSISTENT);
        String text = "Persistent Queue Tutorial! " +
                DateFormat.getDateTimeInstance().format(new Date());
        msg.setText(text);
        // Delivery not yet confirmed. See ConfirmedPublish.java
        prod.send(msg, queue);


        Thread.sleep(2000); //Needed
        prod.close();
        cons.close();
        session.closeSession();
    }
}

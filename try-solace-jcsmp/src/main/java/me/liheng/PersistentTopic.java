package me.liheng;

import com.solacesystems.jcsmp.*;

import java.io.IOException;

public class PersistentTopic {

    // Queues set up in Broker:
    // persistent/one -> queue1
    //                -> queue2
    // persistent/two -> queue2
    public static void main(String[] args) throws IOException, JCSMPException, InterruptedException {

        final JCSMPSession session = Util.getSession();

        // create the queue object locally
        String queueName = "queue1";
        final Queue queue = JCSMPFactory.onlyInstance().createQueue(queueName);
        // set queue permissions to "consume" and access-type to "exclusive"
        final EndpointProperties endpointProps = new EndpointProperties();
        endpointProps.setPermission(EndpointProperties.PERMISSION_CONSUME);
        endpointProps.setAccessType(EndpointProperties.ACCESSTYPE_EXCLUSIVE);
        // Actually provision it, and do not fail if it already exists
        session.provision(queue, endpointProps, JCSMPSession.FLAG_IGNORE_ALREADY_EXISTS);

        // create the queue object locally
        String queue2Name = "queue2";
        final Queue queue2 = JCSMPFactory.onlyInstance().createQueue(queue2Name);
        // set queue permissions to "consume" and access-type to "exclusive"
        final EndpointProperties endpointProps2 = new EndpointProperties();
        endpointProps2.setPermission(EndpointProperties.PERMISSION_CONSUME);
        endpointProps2.setAccessType(EndpointProperties.ACCESSTYPE_EXCLUSIVE);
        // Actually provision it, and do not fail if it already exists
        session.provision(queue2, endpointProps2, JCSMPSession.FLAG_IGNORE_ALREADY_EXISTS);


        final ConsumerFlowProperties flow_prop = new ConsumerFlowProperties();
        flow_prop.setEndpoint(queue);
        flow_prop.setAckMode(JCSMPProperties.SUPPORTED_MESSAGE_ACK_CLIENT);
        EndpointProperties endpoint_props = new EndpointProperties();
        endpoint_props.setAccessType(EndpointProperties.ACCESSTYPE_EXCLUSIVE);
        final FlowReceiver cons = session.createFlow(new XMLMessageListener() {
            @Override
            public void onReceive(BytesXMLMessage msg) {
                if (msg instanceof TextMessage) {
                    System.out.printf(queueName + " TextMessage received: '%s'%n", ((TextMessage) msg).getText());
                } else {
                    System.out.println("Message received.");
                }
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


        final ConsumerFlowProperties flow_prop2 = new ConsumerFlowProperties();
        flow_prop2.setEndpoint(queue2);
        flow_prop2.setAckMode(JCSMPProperties.SUPPORTED_MESSAGE_ACK_CLIENT);
        EndpointProperties endpoint_props2 = new EndpointProperties();
        endpoint_props2.setAccessType(EndpointProperties.ACCESSTYPE_EXCLUSIVE);
        final FlowReceiver cons2 = session.createFlow(new XMLMessageListener() {
            @Override
            public void onReceive(BytesXMLMessage msg) {
                if (msg instanceof TextMessage) {
                    System.out.printf(queue2Name + " TextMessage received: '%s'%n", ((TextMessage) msg).getText());
                } else {
                    System.out.println("Message received.");
                }
                // When the ack mode is set to SUPPORTED_MESSAGE_ACK_CLIENT,
                // guaranteed delivery messages are acknowledged after
                // processing
                msg.ackMessage();
            }
            @Override
            public void onException(JCSMPException e) {
                System.out.printf("Consumer received exception: %s%n", e);
            }
        }, flow_prop2, endpoint_props2);
        cons2.start();



        XMLMessageProducer prod = session.getMessageProducer(new JCSMPStreamingPublishEventHandler() {
            @Override
            public void responseReceived(String messageID) {
                System.out.println("Producer received response for msg: " + messageID);
            }
            @Override
            public void handleError(String messageID, JCSMPException e, long timestamp) {
                System.out.printf("Producer received error for msg: %s@%s - %s%n",
                        messageID,timestamp,e);
            }
        });

        final Topic topic1 = JCSMPFactory.onlyInstance().createTopic("persistent/one");
        final Topic topic2 = JCSMPFactory.onlyInstance().createTopic("persistent/two");
        TextMessage msg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
        final String text = "Msg 1";
        msg.setText(text);
        prod.send(msg,topic1);
        msg.setText("Msg 2");
        prod.send(msg,topic2);


        Thread.sleep(2000); //Needed
        prod.close();
        cons.close();
        cons2.close();
        session.closeSession();
    }
}

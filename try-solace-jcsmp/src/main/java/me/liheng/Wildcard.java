package me.liheng;

import com.solacesystems.jcsmp.*;

import java.io.IOException;

public class Wildcard {

    public static void main(String[] args) throws JCSMPException, InterruptedException, IOException {
        final JCSMPSession session = Util.getSession();

        final XMLMessageConsumer cons = session.getMessageConsumer(new XMLMessageListener(){
            @Override
            public void onReceive(BytesXMLMessage msg) {
                if (msg instanceof TextMessage) {
                    System.out.printf("TextMessage received from '%s': '%s'%n",
                            msg.getDestination(),
                            ((TextMessage)msg).getText());
                } else {
                    System.out.println("Message received.");
                }
            }

            @Override
            public void onException(JCSMPException e) {
                System.out.printf("Consumer received exception: %s%n",e);
            }
        });

        final XMLMessageProducer prod = session.getMessageProducer(new JCSMPStreamingPublishEventHandler() {
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

        final Topic topic1 = JCSMPFactory.onlyInstance().createTopic("tutorial/one");
        final Topic topic2 = JCSMPFactory.onlyInstance().createTopic("tutorial/two");

        //Messages are only received and processed once
        session.addSubscription(JCSMPFactory.onlyInstance().createTopic("tutorial/>"));
        session.addSubscription(JCSMPFactory.onlyInstance().createTopic("tutorial/*"));
        session.addSubscription(topic1);
        session.addSubscription(topic2);
        cons.start();

        TextMessage msg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
        msg.setText("Msg 1");
        prod.send(msg, topic1);

        msg.setText("Msg 2");
        prod.send(msg, topic2);

        msg.setText("Msg 3");
        prod.send(msg, JCSMPFactory.onlyInstance().createTopic("random"));


        Thread.sleep(2000); //Needed
        prod.close();
        cons.close();
        session.closeSession();
    }
}

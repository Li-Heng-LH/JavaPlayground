package me.liheng;

import com.solacesystems.jcsmp.*;

import java.io.IOException;

public class PubSub {
    public static void main( String[] args ) throws JCSMPException, InterruptedException, IOException {
        final JCSMPSession session = Util.getSession();

        final XMLMessageConsumer cons = session.getMessageConsumer(new XMLMessageListener(){
            @Override
            public void onReceive(BytesXMLMessage msg) {
                if (msg instanceof TextMessage) {
                    System.out.printf("TextMessage received: '%s'%n",
                            ((TextMessage)msg).getText());
                } else {
                    System.out.println("Message received.");
                }
                System.out.printf("Message Dump:%n%s%n",msg.dump());
            }

            @Override
            public void onException(JCSMPException e) {
                System.out.printf("Consumer received exception: %s%n",e);
            }
        });

        final Topic topic1 = JCSMPFactory.onlyInstance().createTopic("tutorial/topic1");
        final Topic topic2 = JCSMPFactory.onlyInstance().createTopic("tutorial/topic2");
        final Topic topic3 = JCSMPFactory.onlyInstance().createTopic("tutorial/topic3");
        session.addSubscription(topic1);
        session.addSubscription(topic2);
        cons.start();

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

        TextMessage msg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
        final String text = "Hello world!";
        msg.setText(text);
        prod.send(msg,topic1);

        msg.setText("Msg 2");
        prod.send(msg,topic2);

        msg.setText("Msg 3");
        prod.send(msg,topic3);


        Thread.sleep(2000); //Needed
        prod.close();
        cons.close();
        session.closeSession();
    }
}

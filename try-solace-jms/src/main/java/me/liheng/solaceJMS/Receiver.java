package me.liheng.solaceJMS;

import com.solacesystems.jms.SolJmsUtility;

import javax.jms.*;

public class Receiver {

    public static void run() {
        try {
            Topic topic1 = Util.getSession().createTopic("topic/zero");
            MessageConsumer messageConsumer1 = Util.getSession().createConsumer(topic1);

            Topic topic2 = Util.getSession().createTopic("topic/one");
            MessageConsumer messageConsumer2 = Util.getSession().createConsumer(topic2);

            System.out.println("< Receiving messages...");

            messageConsumer1.setMessageListener(message -> {
                try {
                    if (message instanceof TextMessage) {
                        System.out.printf("< TextMessage received: '%s'%n", ((TextMessage) message).getText());
                    } else {
                        System.out.println("< Message received.");
                    }
                    System.out.printf("Message Content:%n%s%n", SolJmsUtility.dumpMessage(message));
                } catch (JMSException ex) {
                    System.out.println("Error processing incoming message.");
                    ex.printStackTrace();
                }
            });

            messageConsumer2.setMessageListener(message -> {
                try {
                    if (message instanceof TextMessage) {
                        System.out.printf("< TextMessage received: '%s'%n", ((TextMessage) message).getText());
                    } else {
                        System.out.println("< Message received.");
                    }
                    System.out.printf("Message Content:%n%s%n", SolJmsUtility.dumpMessage(message));
                } catch (JMSException ex) {
                    System.out.println("Error processing incoming message.");
                    ex.printStackTrace();
                }
            });

            while(true) {
                //no-op
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        run();
    }
}

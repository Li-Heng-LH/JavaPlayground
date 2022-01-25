package me.liheng.solaceJMS;

import javax.jms.*;

public class Publisher {

    public static void run() {
        try {
            Topic topic = Util.getSession().createTopic("topic/zero");
            MessageProducer messageProducer = Util.getSession().createProducer(topic);
            TextMessage message = Util.getSession().createTextMessage("Hello world!");
            messageProducer.send(topic, message, DeliveryMode.NON_PERSISTENT,
                    Message.DEFAULT_PRIORITY, Message.DEFAULT_TIME_TO_LIVE);
            System.out.println("> Sent msg to " + topic.getTopicName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        run();
    }
}

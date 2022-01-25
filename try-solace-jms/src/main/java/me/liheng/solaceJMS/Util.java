package me.liheng.solaceJMS;

import com.solacesystems.jms.SolConnectionFactory;
import com.solacesystems.jms.SolJmsUtility;

import javax.jms.Connection;
import javax.jms.Session;
import java.io.FileInputStream;
import java.util.Properties;

public class Util {

    private static Session session;

    private Util() {
        //private constructor
    }

    public static Session getSession() throws Exception {
        if (session == null) {
            System.out.println("*** Creating Session... ***");
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/solace-cloud.properties"));

            SolConnectionFactory connectionFactory = SolJmsUtility.createConnectionFactory();
            connectionFactory.setHost(properties.getProperty("cloud-SMF-host"));
            connectionFactory.setVPN(properties.getProperty("cloud-vpn"));
            connectionFactory.setUsername(properties.getProperty("cloud-username"));
            connectionFactory.setPassword(properties.getProperty("cloud-password"));
            Connection connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();
        }
        return session;
    }
}

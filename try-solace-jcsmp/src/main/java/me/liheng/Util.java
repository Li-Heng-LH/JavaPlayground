package me.liheng;

import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPProperties;
import com.solacesystems.jcsmp.JCSMPSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {

    private Util() {
        //no-op
    }

    public static JCSMPSession getSession() throws IOException, JCSMPException {
        Properties loaded = new Properties();
        loaded.load(new FileInputStream("src/main/resources/solace-cloud.properties"));

        final JCSMPProperties properties = new JCSMPProperties();
        properties.setProperty(JCSMPProperties.HOST, loaded.getProperty("cloud-SMF-host"));
        properties.setProperty(JCSMPProperties.USERNAME, loaded.getProperty("cloud-username"));
        properties.setProperty(JCSMPProperties.VPN_NAME, loaded.getProperty("cloud-vpn"));
        properties.setProperty(JCSMPProperties.PASSWORD, loaded.getProperty("cloud-password"));
        final JCSMPSession session = JCSMPFactory.onlyInstance().createSession(properties);
        session.connect();

        return session;
    }
}

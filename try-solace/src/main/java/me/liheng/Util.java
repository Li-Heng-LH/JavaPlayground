package me.liheng;

import com.solace.messaging.config.SolaceProperties;

import java.util.Properties;

public class Util {

    public static final String [] topicStrings = new String [] {
            "topic/zero", "topic/one", "topic/two", "topic/three", "topic/four"};

    public static final String[] persistentTopics = new String[] {
            "persistent/one", "persistent/two"
    };

    public static final String userName = "client";

    public static final String password = "client";

    public static Properties getProperties() {
        final Properties properties = new Properties();
        properties.setProperty(SolaceProperties.TransportLayerProperties.HOST, "localhost:55554");
        properties.setProperty(SolaceProperties.ServiceProperties.VPN_NAME,  "default");
        return properties;
    }
}

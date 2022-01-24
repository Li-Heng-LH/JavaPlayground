package me.liheng.cloud;

import com.solace.messaging.MessagingService;
import com.solace.messaging.config.AuthenticationStrategy;
import com.solace.messaging.config.SolaceProperties;
import com.solace.messaging.config.profile.ConfigurationProfile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CloudUtil {

    private static MessagingService messagingService;

    private CloudUtil() {
        //private constructor
    }

    public static MessagingService getMessagingService() throws IOException {
        if(messagingService == null) {

            System.out.println("Testing singleton...");

            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/solace-cloud.properties"));

            Properties cloudProperties = new Properties();
            cloudProperties.setProperty(SolaceProperties.TransportLayerProperties.HOST, properties.getProperty("cloud-SMF-host"));
            cloudProperties.setProperty(SolaceProperties.ServiceProperties.VPN_NAME, properties.getProperty("cloud-vpn"));

            messagingService = MessagingService.builder(ConfigurationProfile.V1)
                    .fromProperties(cloudProperties)
                    .withAuthenticationStrategy(AuthenticationStrategy.BasicUserNamePassword.of(
                            properties.getProperty("cloud-username"),
                            properties.getProperty("cloud-password")))
                    .build().connect();  // blocking connect to the broker

        }

        return messagingService;
    }
}

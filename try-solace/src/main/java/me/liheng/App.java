package me.liheng;

import com.solace.messaging.MessagingService;
import com.solace.messaging.config.SolaceProperties;
import com.solace.messaging.config.profile.ConfigurationProfile;
import com.solace.messaging.publisher.DirectMessagePublisher;
import com.solace.messaging.publisher.OutboundMessage;
import com.solace.messaging.publisher.OutboundMessageBuilder;
import com.solace.messaging.receiver.DirectMessageReceiver;
import com.solace.messaging.receiver.MessageReceiver;
import com.solace.messaging.resources.Topic;
import com.solace.messaging.resources.TopicSubscription;

import java.util.Properties;

public class App {

    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "Hello World!" );

        final Properties properties = new Properties();
        properties.setProperty(SolaceProperties.TransportLayerProperties.HOST, "localhost:55554");
        properties.setProperty(SolaceProperties.ServiceProperties.VPN_NAME,  "default");     // message-vpn
        properties.setProperty(SolaceProperties.AuthenticationProperties.SCHEME_BASIC_USER_NAME, "client");// client-username
        properties.setProperty(SolaceProperties.AuthenticationProperties.SCHEME_BASIC_PASSWORD, "client");

        //TODO: test with cloud

        final MessagingService messagingService = MessagingService.builder(ConfigurationProfile.V1)
                .fromProperties(properties).build().connect();  // blocking connect to the broker

        final DirectMessagePublisher publisher = messagingService.createDirectMessagePublisherBuilder()
                .onBackPressureWait(1).build().start();

        final DirectMessageReceiver receiver = messagingService.createDirectMessageReceiverBuilder()
                .withSubscriptions(TopicSubscription.of("solace/samples/" + "*/hello/>")).build().start();

        final MessageReceiver.MessageHandler messageHandler = (inboundMessage) -> {
            System.out.printf("vvv RECEIVED A MESSAGE vvv%n%s===%n",inboundMessage.dump());  // just print
        };

        receiver.receiveAsync(messageHandler);

        System.out.printf("%nConnected and subscribed. Ready to publish. Press [ENTER] to quit.%n");
        System.out.printf(" ~ Run this sample twice splitscreen to see true publish-subscribe. ~%n%n");

        OutboundMessageBuilder messageBuilder = messagingService.messageBuilder();
        OutboundMessage message = messageBuilder.build("Hello world!!!");
        String topicString = "solace/samples/" + "java/hello/" + "lh";
        System.out.printf(">> Calling send() on %s%n",topicString);
        publisher.publish(message, Topic.of(topicString));


        Thread.sleep(5000);  // take a pause

//        publisher.terminate(500);
//        receiver.terminate(500);
//        messagingService.disconnect();

        //TODO: Separate into pub and sub components
    }
}

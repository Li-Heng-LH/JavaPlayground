package me.liheng;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker //enables a in-memory message broker

public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //for messages sent from UI to server
        //Server destination topic prefix
        //to carry the greeting messages back to the client on destinations prefixed with /topic.
        config.enableSimpleBroker("/topic");

        //UI destination topic prefix
        //designates the /app prefix for messages that are bound for methods annotated with @MessageMapping
        // /app/hello is the endpoint that the UserController.getUser() method is mapped to handle.
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //enabling Spring’s STOMP support
        registry.addEndpoint("/websocket-example").withSockJS();
    }
}

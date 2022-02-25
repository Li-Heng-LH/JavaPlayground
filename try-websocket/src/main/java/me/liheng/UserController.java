package me.liheng;

import me.liheng.model.User;
import me.liheng.model.UserResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller //STOMP messages are routed to controller
public class UserController {

    @MessageMapping("/hello") //this method to be called when a msg sent to this endpoint
    //Associate a controller method to the configured endpoint

    @SendTo("/topic/greetings") //send the result to this endpoint
    //All subscribers to the “/topic/greetings” destination will receive the result

    public UserResponse getUser(User user) {
        return new UserResponse("Hello " + user.getName());
    }


}

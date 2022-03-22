package me.liheng;

import me.liheng.protobuf.Person;
import me.liheng.protobuf.UserOuterClass;

public class App {
    public static void main( String[] args ) {
        Person person1 = Person.newBuilder().setId(1).setName("Hello").build();
        System.out.println(person1);

        UserOuterClass.User user = UserOuterClass.User.newBuilder()
                .setId(2).setName("World").build();
        System.out.println(user);
    }
}

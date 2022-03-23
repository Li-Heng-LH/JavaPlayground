package me.liheng;

import me.liheng.protobuf.Person;
import me.liheng.protobuf.UserOuterClass;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class App {
    public static void main( String[] args ) throws IOException {
        Person person1 = Person.newBuilder().setId(1).setName("Hello").build();
        System.out.println(person1);

        UserOuterClass.User user = UserOuterClass.User.newBuilder()
                .setId(2).setName("World").build();
        System.out.println(user);

        Person emptyPerson = Person.newBuilder().build();
        System.out.println(emptyPerson);

        person1.writeDelimitedTo(new FileOutputStream("src/main/resources/test"));
        Person parsedPerson = Person.parseDelimitedFrom(new FileInputStream("src/main/resources/test"));
        System.out.println(parsedPerson);

        System.out.println(Person.getDescriptor().getName());
        System.out.println(Person.getDescriptor().getFullName());
        System.out.println(Person.getDescriptor().getFields());
    }
}

package me.liheng;

import me.liheng.protobuf.Person;

public class App {
    public static void main( String[] args ) {
        Person person1 = Person.newBuilder().setId(1).setName("Hello").build();
        System.out.println(person1);
    }
}

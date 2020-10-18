package me.liheng;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(new App().getGreeting());
        }
        else {
            for (String arg: args) {
                System.out.println(arg);
            }
        }

        // pass in command line: -DmyProperty=apple
        System.out.println(System.getProperty("myProperty"));
    }
}

package me.liheng.StringPools;

public class Main {

    public static void main(String[] args) {
        String hello1 = "hello";
        String hello2 = "hello";
        System.out.println("hello1 == hello2: " + (hello1 == hello2)); //True. Both pointing to the same object.

        String number1 = new Integer(76).toString(); //"76"
        String number2 = "76";
        System.out.println("number1 == number2: " + (number1 == number2)); //False. Calculated.

        String concat1 = "con" + "cat";
        String concat2 = "concat";
        System.out.println("concat1 == concat2: " + (concat1 == concat2)); //True.

        String number3 = new Integer(76).toString().intern(); //"76"
        String number4 = "76";
        System.out.println("number3 == number4: " + (number3 == number4)); //True.
    }
}

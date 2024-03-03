package org.example;

import java.io.FileInputStream;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws Exception {

        Properties prop = new Properties();
        //load a properties file from class path, inside static method
        prop.load(new FileInputStream("src/main/resources/mongo.properties"));

        //get the property value and print it out
        System.out.println(prop.getProperty("connectionString"));

    }
}

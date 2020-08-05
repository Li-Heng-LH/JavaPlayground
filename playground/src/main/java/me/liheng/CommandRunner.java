package me.liheng;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

//Tutorial to run a Shell Command in Java
public class CommandRunner {

    public static void run() throws IOException, InterruptedException {
        System.out.println(System.getProperty("os.name").toLowerCase());

        ProcessBuilder builder = new ProcessBuilder();
        builder.command("mvn", "dependency:list");
        builder.directory(new File("6.13.0"));
        Process process = builder.start();

        BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = r.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        assert exitCode == 0;

        System.out.println("End of shell cmd");

    }

}

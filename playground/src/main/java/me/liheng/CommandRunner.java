package me.liheng;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//Tutorial to run a Shell Command in Java
public class CommandRunner {

    public static void run() throws IOException, InterruptedException {
        System.out.println(System.getProperty("os.name").toLowerCase());

        Path originalPath = Paths.get("");
        boolean needToDeletePom = false;
        if (! new File("6.13.0/pom.xml").exists()) {
            File dir = new File("6.13.0");
            for (File file : dir.listFiles()) {
                if (file.getName().toLowerCase().endsWith((".pom"))) {
                    originalPath = file.toPath();
                    break;
                }
            }
            Path copiedPomPath = Paths.get("6.13.0/pom.xml");
            Files.copy(originalPath, copiedPomPath);
            needToDeletePom = true;
        }


        ProcessBuilder builder = new ProcessBuilder();
        builder.directory(new File("6.13.0"));
        builder.command("mvn", "dependency:list");
        Process process = builder.start();

        BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = r.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        assert exitCode == 0;


        if (needToDeletePom) {
            Files.delete(Paths.get("6.13.0/pom.xml"));
            needToDeletePom = false;
        }


    }

}

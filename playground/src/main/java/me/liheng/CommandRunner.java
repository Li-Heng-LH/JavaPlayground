package me.liheng;

import org.apache.commons.lang3.StringUtils;
import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.api.OutputSinkFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import me.liheng.CFRRunner;

//Tutorial to run a Shell Command in Java
public class CommandRunner {

    public static void run() throws IOException, InterruptedException {
        System.out.println(System.getProperty("os.name").toLowerCase());


        List<String> jarPaths= new ArrayList<>();


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
        builder.command("mvn", "dependency:list", "-DoutputAbsoluteArtifactFilename");
        Process process = builder.start();

        BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String dependencyLine;
        while ((dependencyLine = r.readLine()) != null) {
            if ((StringUtils.countMatches(dependencyLine, ":") >= 5) &&
                    (dependencyLine.contains(":compile:") || dependencyLine.contains(":provided:") || dependencyLine.contains(":system:") || dependencyLine.contains(":import:"))) {

                String[] tokens = dependencyLine.split(":");
                jarPaths.add(tokens[tokens.length - 1]);
            }
        }

        int exitCode = process.waitFor();
        assert exitCode == 0;

        if (needToDeletePom) {
            Files.delete(Paths.get("6.13.0/pom.xml"));
            needToDeletePom = false;
        }












        Instant start = Instant.now();
        for (String jarPath : jarPaths) {

            // Create a stream to hold the output
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);

            OutputSinkFactory mySink = new OutputSinkFactory() {
                @Override
                public List<SinkClass> getSupportedSinks(SinkType sinkType, Collection<SinkClass> collection) {
                    // I only understand how to sink strings, regardless of what you have to give me.
                    return Collections.singletonList(SinkClass.STRING);
                }

                @Override
                public <T> Sink<T> getSink(SinkType sinkType, SinkClass sinkClass) {
                    return sinkType == SinkType.JAVA ? ps::println : ignore -> {};
                }
            };

            CfrDriver driver = new CfrDriver.Builder().withOutputSink(mySink).build();

            driver.analyse(Collections.singletonList(jarPath));

            ps.flush();


            // Show what happened

//        int classCount = 0;
            String [] lines =  baos.toString().split(System.lineSeparator());
            baos.close();
            ps.close();
//        for (String line : lines) {
//            if (line.contains("Analysing type ")) classCount++;
//        }
//        System.out.println("class count: " + classCount); //2811


            StringBuilder sb = new StringBuilder();
            int classCount = 0;

            boolean afterPackage = false;
            for (String line : lines) {
                line = line.trim().replaceAll(" +", " ");

                if (line.startsWith("package ") && line.endsWith(";")) {
                    String packageNameWithSemicolon = line.split(" ")[1];
                    packageNameWithSemicolon = packageNameWithSemicolon.replaceAll(" +", "");
                    String packageName = packageNameWithSemicolon.substring(0, packageNameWithSemicolon.length() - 1);
                    sb.append(packageName);
                    afterPackage = true;
                } else if (afterPackage && line.startsWith("import ") && line.endsWith(";")) {

                } else if (afterPackage
                        && (line.contains("class ") || line.contains("interface ") || line.contains("enum "))
                        && !line.startsWith("//") && !line.startsWith("/*") && !line.startsWith("*")) {

                    String [] tokens = line.split(" ");
                    int i;
                    for (i = 0; i < tokens.length; i++) {
                        if (tokens[i].contains("class") || tokens[i].contains("interface") || tokens[i].contains("enum")) break;
                    }
                    sb.append(":").append(tokens[++i].split("<")[0]);
                    System.out.println(sb.toString());

                    classCount++;

                    afterPackage = false;

                    sb.setLength(0);
                }
            }

            System.out.println("Here: " + classCount);
        }





        Instant finish = Instant.now();
        System.out.println("Time: " + Duration.between(start, finish).toMillis());



        CFRRunner.run();
    }
}

package me.liheng;

import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.api.OutputSinkFactory;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class CFRRunner {

    static final String JUNIT_PATH = "4.12/junit-4.12.jar";
    static final String JASPER_PATH = "6.13.0/jasperreports-6.13.0.jar";

    public static void run() throws IOException {
        Instant start = Instant.now();

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

        driver.analyse(Collections.singletonList(JUNIT_PATH));

        ps.flush();


        // Show what happened

//        int classCount = 0;
        String [] lines =  baos.toString().split(System.lineSeparator());
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

        Instant finish = Instant.now();
        System.out.println("Here: " + classCount);
        System.out.println("Time: " + Duration.between(start, finish).toMillis());

    }

}

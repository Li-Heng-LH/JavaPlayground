package me.liheng;

import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.api.OutputSinkFactory;

import java.io.*;
import java.util.*;

public class CFRRunner {

    static final String JUNIT_PATH = "4.12/junit-4.12.jar";
    static final String JASPER_PATH = "6.13.0/jasperreports-6.13.0.jar";

    public static void run() throws IOException {

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
        String [] lines =  baos.toString().split("\\r?\\n");
        System.out.println("Here: " + lines.length);
        for (int i = 0; i <11; i++) {
            System.out.println(lines[i]);
        }

    }

}

package me.liheng;

import java.io.File;
import java.io.IOException;

public class ProcyonCmdRunner {

    public static void run() throws IOException, InterruptedException {

        Runtime rt = Runtime.getRuntime();

        Process pr = rt.exec("java -jar decompiler.jar -jar 6.13.0/jasperreports-6.13.0.jar -o out");

        int exitCode = pr.waitFor();
        assert exitCode == 0;

        System.out.println("End of shell cmd");

    }
}

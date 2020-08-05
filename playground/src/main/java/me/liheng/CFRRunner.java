package me.liheng;

import org.benf.cfr.reader.api.CfrDriver;
import java.util.Arrays;
import java.util.Collections;

public class CFRRunner {

    public static void run() {

        CfrDriver driver = new CfrDriver.Builder().build();
        driver.analyse(Collections.singletonList("6.13.0/jasperreports-6.13.0.jar"));

    }
}

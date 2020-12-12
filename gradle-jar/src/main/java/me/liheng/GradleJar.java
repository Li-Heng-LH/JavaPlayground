package me.liheng;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.math3.util.MathUtils.TWO_PI;

public class GradleJar {

    public static void main(String[] args) {

        String str = "hello world";
        System.out.println("Original string: " + str);
        System.out.println("After StringUtils: " + StringUtils.capitalize(str));
        System.out.println(TWO_PI);
    }
}

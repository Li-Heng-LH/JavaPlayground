package me.liheng;

import org.apache.commons.lang3.StringUtils;

public class TryJar {

    public static void main(String[] args) {

    	String str = "hello world";
        System.out.println("Original string: " + str);
        System.out.println("After StringUtils: " + StringUtils.capitalize(str));
    }
}

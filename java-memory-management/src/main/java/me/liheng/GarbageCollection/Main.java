package me.liheng.GarbageCollection;

public class Main {

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        long availableBytes = runtime.freeMemory();
        System.out.println("Available memory: " + availableBytes / 1024 + "k");

        // let's create a ton of garbage....
        // each newly created object is instantly available for GC
        // because as soon as each loop finishes,
        // the newly created object is no longer referenced.
        Customer c;
        for (int i=0; i<100; i++)
        {
            c = new Customer("John");
        }

        availableBytes = runtime.freeMemory();
        System.out.println("Available memory: " + availableBytes / 1024 + "k");

        // GC may or may not run
        System.gc();

        availableBytes = runtime.freeMemory();
        System.out.println("Available memory: " + availableBytes / 1024 + "k");
    }

}
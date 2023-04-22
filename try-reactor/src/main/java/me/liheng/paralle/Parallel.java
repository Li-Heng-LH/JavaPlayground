package me.liheng.paralle;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Parallel {

    static Scheduler scheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(2));
    static AtomicInteger batchNumber = new AtomicInteger(0);

    public static void main(String[] args) {
        Flux.range(1, 1_000_000)
                .buffer(10_000)
                .parallel(2, 2)
                .runOn(scheduler, 1)
                .map(Parallel::processBatch)
                .flatMap(Flux::fromIterable)
                .sequential()
                .count()
                .subscribe(count -> System.out.println(count == 1_000_000));
    }

    private static Set<Integer> processBatch(List<Integer> batch) {
        System.out.println(Thread.currentThread().getName() + ": Processing Batch: " + batchNumber.addAndGet(1));
        Map<Integer, Integer> map = batch.stream()
                .collect(Collectors.toMap(i -> i, i -> i));
        System.out.println(Thread.currentThread().getName() + ": Map size: " + map.size());
        return map.keySet();
    }

    private static void shutDown() {
        scheduler.dispose();
    }

    /* res:
    pool-1-thread-1: Processing Batch: 6
    pool-1-thread-2: Processing Batch: 7
    pool-1-thread-1: Map size: 10000
    pool-1-thread-2: Map size: 10000
     */
}

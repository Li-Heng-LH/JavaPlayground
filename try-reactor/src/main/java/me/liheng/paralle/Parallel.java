package me.liheng.paralle;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

public class Parallel {

    static Scheduler scheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(2));

    public static void main(String[] args) {
        Flux.range(1, 50)
                .buffer(10)
                .parallel(2, 2)
                .runOn(scheduler, 1)
                .map(Utils::processBatch)
                .flatMap(Flux::fromIterable)
                .sequential()
                .subscribe(System.out::println);
                //.count()
                //.subscribe(count -> System.out.println(count == 1_000_000));
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

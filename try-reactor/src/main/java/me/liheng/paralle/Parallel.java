package me.liheng.paralle;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

public class Parallel {

    static Scheduler scheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(2));

    public static void main(String[] args) {
        Flux.range(1, 1_000_000)
                .buffer(1_000)
                .parallel(2, 2)
                .runOn(scheduler, 1)
                //.map()
                .flatMap(Flux::fromIterable)
                .sequential()
                .subscribe();
    }

    private static void shutDown() {
        scheduler.dispose();
    }
}

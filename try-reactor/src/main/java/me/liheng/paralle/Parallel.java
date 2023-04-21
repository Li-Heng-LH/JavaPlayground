package me.liheng.paralle;

import reactor.core.publisher.Flux;

public class Parallel {

    public static void main(String[] args) {
        Flux.range(1, 3)
                .subscribe(System.out::println);
    }
}

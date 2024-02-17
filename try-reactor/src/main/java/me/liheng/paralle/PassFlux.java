package me.liheng.paralle;

import reactor.core.publisher.Flux;

public class PassFlux {

    public static void main(String[] args) {
        modifyFlux(getFlux())
                .subscribe(System.out::println);
    }

    private static Flux<Integer> getFlux() {
        return Flux.range(1, 20);
    }
    private static Flux<Integer> modifyFlux(Flux<Integer> flux) {
        return flux.map(i -> i * 3);
    }
}

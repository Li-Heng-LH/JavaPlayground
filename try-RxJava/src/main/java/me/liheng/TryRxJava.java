package me.liheng;


import io.reactivex.Observable;

public class TryRxJava {

    public static void main( String[] args ) {

        // completely synchronous
        // Observable.create --> Observable
        // Observable.subscribe --> Disposable/Subscription
        // The Observable type is lazy, meaning it does nothing until it is subscribed to
        // Because Observable is lazy, Observables can be reused
        Observable.create(s -> {
            s.onNext("Hello World!");
            s.onComplete();
        }).subscribe(System.out::println);
    }
}

package me.liheng;

import io.reactivex.rxjava3.core.Flowable;

public class TryRxJava {

    public static void main( String[] args ) {

        Flowable.just("Hello world").subscribe(System.out::println);
    }
}

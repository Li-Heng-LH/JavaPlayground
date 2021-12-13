package me.liheng;

import io.reactivex.rxjava3.core.Observable;

public class TryRxJava {

    public static void main( String[] args ) {

        Observable.just("Hello world").subscribe(System.out::println);
    }
}

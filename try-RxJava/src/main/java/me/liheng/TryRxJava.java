package me.liheng;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class TryRxJava {

    public static void main( String[] args ) {

        Observable.just("Hello", "world").subscribe(o -> {
            Thread.sleep(3000);
            System.out.println(o);
        });
    }
}

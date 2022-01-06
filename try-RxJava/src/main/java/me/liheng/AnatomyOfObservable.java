package me.liheng;

import io.reactivex.Observable;

public class AnatomyOfObservable {

    public static void main(String[] args) {

        Observable<String> strings = Observable.just("Hello", "World");

        // Below code subscribes to strings by registering a callback
        // the callback consumer takes in a String
        // this callback will be invoked everytime the strings stream decides to
        // push an event downstream
        // RxJava ensures that the callback will not be invoked from more than one thread at a time,
        // even though events can be emitted from many threads
        strings.subscribe((String string) -> System.out.println(string),
                (Throwable t) -> t.printStackTrace(),
                () -> System.out.println("No More.")
        );
    }
}

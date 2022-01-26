package me.liheng;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UsingObserver {

    public static void main(String[] args) {

        // Container for all 3 callbacks
        // Anonymous Inner Class
        Observer<String> printObserver = new Observer<String>() {
            private Disposable disposable;

            // Someone will invoke this and pass me a disposable
            @Override
            public void onSubscribe(Disposable disposable) {
                System.out.println("Subscribing");
                this.disposable = disposable;
            }

            // Someone will invoke this and pass me a string
            @Override
            public void onNext(String s) {
                // Disposing
                if (s.equals("C")) disposable.dispose();
                System.out.println(s);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("No more");
            }
        };

        // subscribing using Observer
        Observable.just("A", "B", "C", "D").subscribe(printObserver);
    }
}

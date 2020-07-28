package geekbrains.ru.lesson1mvc.tasks;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class EventBusExample {
    private static String TAG = "EventBusExample";
    private Observable<Long> source1 = Observable.interval(0, TimeUnit.MILLISECONDS).take(100);
    private Observable<Long> source2 = Observable.interval(0, TimeUnit.MILLISECONDS).take(100).map(value->value + 100);

    private Observer<Long> target1 = new Observer<Long>() {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onNext(Long value) {
            Log.i(TAG, String.format("target1: %d", value));
        }

        @Override
        public void onError(Throwable e) {
            Log.i(TAG, "target2: onError");
        }

        @Override
        public void onComplete() {
            Log.i(TAG, "target1: onComplete");
        }
    };

    private Observer<Long> target2 = new Observer<Long>() {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onNext(Long value) {
            Log.i(TAG, String.format("target2: %d", value));
        }

        @Override
        public void onError(Throwable e) {
            Log.i(TAG, "target2: onError");
        }

        @Override
        public void onComplete() {
            Log.i(TAG, "target2: onComplete");
        }
    };

    private PublishSubject<Long> subject = PublishSubject.create();

    public void runTest(){
        source1.subscribe(subject);
        source2.subscribe(subject);
        subject.subscribe(target1);
        subject.subscribe(target2);
        subject.onNext(512L);
    }
}


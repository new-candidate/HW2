package geekbrains.ru.lesson1mvc.presenters;


import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class Lesson2Presenter extends BasePresenter {
    String lastValue = "";

    public Disposable bindView(Observable<String> src, Consumer<String> dst){
        PublishSubject<String> bus = PublishSubject.create();
        Disposable disposable = bus.subscribe(dst);
        bus.onNext(lastValue);
        disposable.dispose();
        return src.map(value->lastValue = value).subscribe(dst);
    }

    public void unbindView(Disposable disposable){
        if(!disposable.isDisposed()) disposable.dispose();
    }
}

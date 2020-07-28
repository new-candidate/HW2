package geekbrains.ru.lesson1mvc.presenters;

import geekbrains.ru.lesson1mvc.Model;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class MainPresenter extends BasePresenter{
    private Model model = new Model();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void unbindView() {  compositeDisposable.clear();  }


    private int buttonClick(int index){
        int newModelValue = model.getElementValueAtIndex(index) + 1;
        model.setElementValueAtIndex(index, newModelValue);
        return newModelValue;
    }

    public void bindButton(int idx, Observable<Integer> src, Consumer<Integer> dst) {
        compositeDisposable.add(src.map(this::buttonClick).subscribe(dst));
        PublishSubject<Integer> bus = PublishSubject.create();
        Disposable disposable = bus.subscribe(dst);
        bus.onNext(model.getElementValueAtIndex(idx));
        disposable.dispose();
    }
}

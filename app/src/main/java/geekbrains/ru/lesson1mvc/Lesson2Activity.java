package geekbrains.ru.lesson1mvc;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import geekbrains.ru.lesson1mvc.presenters.Lesson2Presenter;
import geekbrains.ru.lesson1mvc.presenters.MainPresenter;
import geekbrains.ru.lesson1mvc.presenters.PresenterManager;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

interface SimpleTextWatcher extends TextWatcher {
    @Override
    default void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    @Override
    default void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    void afterTextChanged(Editable editable);
}

public class Lesson2Activity extends Activity {
    private Lesson2Presenter presenter;
    private Disposable disposable;
//    EventBusExample example = new EventBusExample();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson2);
        if (savedInstanceState == null) {
            presenter = new Lesson2Presenter();
        } else {
            presenter = (Lesson2Presenter) PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Observable<String> observable = Observable.create(emitter -> ((EditText) findViewById(R.id.observable))
                .addTextChangedListener((SimpleTextWatcher) (editable -> emitter.onNext(editable.toString()))));
        disposable = presenter.bindView(observable,value->((TextView)findViewById(R.id.observer)).setText(value));
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbindView(disposable);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(presenter, outState);
    }
}

package geekbrains.ru.lesson1mvc;

import androidx.appcompat.app.AppCompatActivity;
import geekbrains.ru.lesson1mvc.presenters.MainPresenter;
import geekbrains.ru.lesson1mvc.presenters.PresenterManager;
import geekbrains.ru.lesson1mvc.tasks.EventBusExample;
import io.reactivex.Observable;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            presenter = new MainPresenter();
        } else {
            presenter = (MainPresenter) PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }
    }

    private void initButton(int id, int idx){
        Button button = findViewById(id);
        Observable<Integer> observable =
                Observable.create(emitter-> button.setOnClickListener(v-> emitter.onNext(idx)));
        presenter.bindButton(idx, observable, value->button.setText("Количество = " + value));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initButton(R.id.btnCounter1,0);
        initButton(R.id.btnCounter2,1);
        initButton(R.id.btnCounter3,2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbindView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(presenter, outState);
    }

    public void lesson2_1(MenuItem item) {
        startActivity(new Intent(this, Lesson2Activity.class));
    }

    public void lesson2_2(MenuItem item) {
        EventBusExample example = new EventBusExample();
        example.runTest();
    }
}


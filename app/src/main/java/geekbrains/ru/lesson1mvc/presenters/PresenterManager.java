package geekbrains.ru.lesson1mvc.presenters;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class PresenterManager {
    private static final String PRESENTER_ID = "presenter_id";
    private static PresenterManager instance;

    private final AtomicLong currentId = new AtomicLong();

    public static PresenterManager getInstance() {
        if (instance == null) {
            instance = new PresenterManager();
        }
        return instance;
    }
    private Map<Long, BasePresenter> presenterMap = new HashMap<>();

    public void savePresenter(BasePresenter presenter, Bundle bundle){
        Long id = currentId.incrementAndGet();
        presenterMap.put(id, presenter);
        bundle.putLong(PRESENTER_ID, id);
    }

    public BasePresenter restorePresenter(Bundle bundle){
        long id = bundle.getLong(PRESENTER_ID, -1);

        if(!presenterMap.containsKey(id)) return new MainPresenter();

        BasePresenter presenter = presenterMap.get(id);
        presenterMap.remove(presenter);
        return presenter;
    }
}

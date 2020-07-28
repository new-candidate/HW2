package geekbrains.ru.lesson1mvc;

import android.util.Log;
import java.util.Arrays;
import java.util.List;

public class Model {
    public static final String TAG = Model.class.getName();

    private List<Integer> list = Arrays.asList(0,0,0);

    public int getElementValueAtIndex(int index){
        if(index < 0 || index > list.size()) {
            Log.e(TAG, "index " + index + " out of bound");
            return 0;
        }
        return list.get(index);
    }

    public void setElementValueAtIndex(int index, int value){
        if(index < 0 || index > list.size()) {
            Log.e(TAG, "index " + index + " out of bound");
            return;
        }
        list.set(index, value);

    }

    public int getElementsCount(){
        return list.size();
    }
}


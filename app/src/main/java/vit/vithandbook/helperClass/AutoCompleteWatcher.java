package vit.vithandbook.helperClass;

import android.text.Editable;
import android.text.TextWatcher;
import vit.vithandbook.activity.MainActivity;

/**
 * Created by pulkit on 06/07/2015.
 */
public class AutoCompleteWatcher implements TextWatcher
{
    MainActivity mainActivity;
    MainActivity.searchTask mySearchTask;
    public AutoCompleteWatcher(MainActivity mainActivity)
    {
        this.mainActivity=mainActivity;
    }
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
    public void afterTextChanged(Editable s) {
        if (s != null && !s.toString().equals("")) {

            if (mySearchTask == null) {
                mySearchTask = mainActivity.new searchTask(mainActivity);

            } else {

                mySearchTask.cancel(true);
                mySearchTask = mainActivity.new searchTask(mainActivity);

            }
            mySearchTask.execute(s.toString());

        } else {

            if (mySearchTask != null) {
                mySearchTask.cancelAndClear();
            }
        }

    }
}

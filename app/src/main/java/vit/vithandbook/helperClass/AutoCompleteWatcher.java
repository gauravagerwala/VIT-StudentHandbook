package vit.vithandbook.helperClass;

import android.text.Editable;
import android.text.TextWatcher;

import vit.vithandbook.activity.SearchActivity;

/**
 * Created by pulkit on 06/07/2015.
 */
public class AutoCompleteWatcher implements TextWatcher
{
    SearchActivity searchActivity;
    SearchActivity.searchTask mySearchTask;
    public AutoCompleteWatcher(SearchActivity searchActivity)
    {
        this.searchActivity =searchActivity;
    }
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
    public void afterTextChanged(Editable s) {
        if (s != null && !s.toString().equals("")) {

            if (mySearchTask == null) {
                mySearchTask = searchActivity.new searchTask(searchActivity);

            } else {

                mySearchTask.cancel(true);
                mySearchTask = searchActivity.new searchTask(searchActivity);

            }
            mySearchTask.execute(s.toString());

        } else {

            if (mySearchTask != null) {
                mySearchTask.cancelAndClear();
            }
        }

    }
}

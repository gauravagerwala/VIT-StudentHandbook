package vit.vithandbook.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import vit.vithandbook.R;

public class SearchActivity extends AppCompatActivity {

    Toolbar toolbar ;
    ActionBar action ;
    EditText edtSeach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = (Toolbar)findViewById(R.id.search_toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.mainHeader));
        setSupportActionBar(toolbar);
        action = getSupportActionBar();
        action.setDisplayShowCustomEnabled(true);
        action.setCustomView(R.layout.search_bar);
        action.setDisplayShowTitleEnabled(false);
        edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch);
        edtSeach.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_close) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

package vit.vithandbook.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import vit.vithandbook.R;

public class ArticleFragment extends Fragment {

    String Topic ;
    View rootView ;
    Toolbar toolbar ;
    boolean bookmarked ;
    Menu menu ;

    public ArticleFragment() {

    }

    public static ArticleFragment newInstance(String Topic) {
        ArticleFragment frag = new ArticleFragment();
        frag.Topic = Topic ;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView =  inflater.inflate(R.layout.fragment_article, container, false);
        toolbar = (Toolbar)rootView.findViewById(R.id.toolbar_article);
        return rootView ;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_article, menu);
        bookmarked = false;
        this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                getActivity().finish();
                return true;
            case R.id.article_bookmark:
                if(!bookmarked){
                    bookmarked = true;
                    menu.getItem(0).setIcon(R.drawable.ic_star_black_24dp);

                    //TODO: add bookmarking feature
                }
                else {
                    bookmarked = false;
                    menu.getItem(0).setIcon(R.drawable.ic_star_border_black_24dp);
                }
        }

        return super.onOptionsItemSelected(item);
    }
}

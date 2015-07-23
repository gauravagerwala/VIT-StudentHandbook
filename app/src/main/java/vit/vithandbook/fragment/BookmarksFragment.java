package vit.vithandbook.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vit.vithandbook.R;


public class BookmarksFragment extends BackHandlerFragment {


    public static BookmarksFragment newInstance()
    {
        BookmarksFragment fragment = new BookmarksFragment();
        return fragment;
    }

    public BookmarksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmarks, container, false);
    }

    @Override
    public boolean onBackPressed() {

        return false;
    }
}

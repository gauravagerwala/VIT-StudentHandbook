package vit.vithandbook.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vit.vithandbook.R;

/**
 * Created by Hemant on 1/21/2016.
 */
public class FeedbackFragment extends BackHandlerFragment {
    View rootView;
    public FeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
        return rootView;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

}

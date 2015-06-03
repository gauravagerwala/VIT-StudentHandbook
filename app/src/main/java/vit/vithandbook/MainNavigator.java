package vit.vithandbook;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainNavigator extends Fragment {

    int AppState = 1 ;
    public MainNavigator()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main_navigator,container,false);
        return view ;
    }
}

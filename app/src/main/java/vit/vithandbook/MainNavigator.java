package vit.vithandbook;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainNavigator extends BackHandlerFragment {

    int AppState = 1 ;
    GridLayout grid ;

    public MainNavigator()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main_navigator, container, false);
        grid = (GridLayout)view.findViewById(R.id.mainNavGrid);
        return view ;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        ViewTreeObserver vto = grid.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = (int)(grid.getWidth());
                allignCards(width);
            }
        });
    }
    void allignCards(int width)
    {
        int cols = grid.getColumnCount();
        int idealW = (width - (30*cols))/cols;
        for(int i = 0 ; i < grid.getChildCount() ; i ++)
        {
           android.support.v7.widget.CardView card = (android.support.v7.widget.CardView)grid.getChildAt(i) ;
            GridLayout.LayoutParams params = (GridLayout.LayoutParams)card.getLayoutParams();
            params.width = idealW ;
            card.setLayoutParams(params);
        }
    }
    @Override
    public boolean onBackPressed()
    {
        return false ;
    }
}

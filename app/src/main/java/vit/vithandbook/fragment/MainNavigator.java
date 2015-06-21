package vit.vithandbook.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;

import vit.vithandbook.R;
import vit.vithandbook.activity.MainActivity;

public class MainNavigator extends BackHandlerFragment {

    GridLayout grid ;
    customScrollView scrollView;

    public MainNavigator()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main_navigator, container, false);
        grid = (GridLayout)view.findViewById(R.id.mainNavGrid);
        scrollView = (customScrollView)view.findViewById(R.id.mainScrollView);
        scrollView.getActivity((MainActivity) getActivity());
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
                int width = grid.getWidth();
                allignCards(width);
            }
        });

    }

    void allignCards(int width)
    {
        int cols = grid.getColumnCount();
        int spacefactor = dptopx(10);
        int idealW = (width/cols)-spacefactor;
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

     int dptopx(int dp)
    {
        float density = getActivity().getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}

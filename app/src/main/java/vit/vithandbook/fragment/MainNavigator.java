package vit.vithandbook.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import vit.vithandbook.R;
import vit.vithandbook.activity.MainActivity;
import vit.vithandbook.helperClass.FragmentSwitchListener;

public class MainNavigator extends BackHandlerFragment {

    ScrollView mainScrollView;
    GridLayout grid;
    public boolean isOnSubLevel = false;
    Fragment subSectionFragment ;
    DisplayMetrics dm ;

    public MainNavigator() {

    }

    public static MainNavigator newInstance()
    {
        MainNavigator frag = new MainNavigator();
        return frag ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_navigator, container, false);
        grid = (GridLayout) view.findViewById(R.id.mainNavGrid);
        mainScrollView = (ScrollView)view.findViewById(R.id.mainScrollView);
        for(int i = 0 ; i < grid.getChildCount() ; i++)
        {
            ((CardView)grid.getChildAt(i)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    navigate(view);
                }
            });
            dm = getActivity().getApplicationContext().getResources().getDisplayMetrics();
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
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

    void allignCards(int width) {
        int cols = grid.getColumnCount();
        int spacefactor = dptopx(5);
        int idealW = (width / cols) - spacefactor;
        for (int i = 0; i < grid.getChildCount(); i++) {
            android.support.v7.widget.CardView card = (android.support.v7.widget.CardView) grid.getChildAt(i);
            GridLayout.LayoutParams params = (GridLayout.LayoutParams) card.getLayoutParams();
            params.width = idealW;
            card.setLayoutParams(params);
        }
    }

    @Override
    public boolean onBackPressed()
    {
        if(isOnSubLevel)
        {
            hideSubLevels();
            return true ;
        }
        return false ;
    }

    int dptopx(int dp) {
        float density = dm.density;
        return Math.round((float) dp * density);
    }

    public void navigate(View view)
    {
        ((MainActivity)getActivity()).AnimateMainHeader((ViewGroup)view,false);
        String category = (String) view.getTag();
        mainScrollView.setVisibility(View.GONE);
        getActivity().getFragmentManager().beginTransaction().add(R.id.linear_layout_bottom,subSectionFragment = SubSectionFragment.newInstance(category),"subSectionFragment").addToBackStack(null).commit();
        isOnSubLevel = true ;
    }

    public void hideSubLevels()
    {
        getActivity().getFragmentManager().beginTransaction().remove(subSectionFragment).commit();
        mainScrollView.setVisibility(View.VISIBLE);
        ((MainActivity)getActivity()).AnimateMainHeader(null,true);
        isOnSubLevel = false ;
    }
}

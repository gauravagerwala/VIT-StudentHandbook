package vit.vithandbook.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import vit.vithandbook.R;
import vit.vithandbook.adapter.MainNavigatorAdapter;
import vit.vithandbook.adapter.onItemClickListener;

public class MainNavigator extends BackHandlerFragment {

    ScrollView mainScrollView;
    GridLayout grid;
    View rootView;
    RecyclerView mainNavigator ;
    MainNavigatorAdapter rvAdapter ;

    public MainNavigator() {

    }

    public static MainNavigator newInstance() {
        MainNavigator frag = new MainNavigator();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_navigator, container, false);
        mainNavigator = (RecyclerView)rootView.findViewById(R.id.rv_main_navigator);
        rvAdapter = new MainNavigatorAdapter(getActivity());
        rvAdapter.setOnItemClickListener(new onItemClickListener() {
            @Override
            public void onItemClick(String data) {
               navigate(data);
            }
        });
        mainNavigator.setAdapter(rvAdapter);
        mainNavigator.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return rootView;
    }

  /*  @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ViewTreeObserver vto = grid.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = grid.getWidth();
                allignCards(width);
                vto.removeOnGlobalLayoutListener(this);
            }
        });

    }*/

   /* void allignCards(int width) {
        int cols = grid.getColumnCount();
        int spacefactor = dptopx(5);
        int idealW = (width / cols) - spacefactor;
        for (int i = 0; i < grid.getChildCount(); i++) {
            android.support.v7.widget.CardView card = (android.support.v7.widget.CardView) grid.getChildAt(i);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigate(v);
                }
            });
            GridLayout.LayoutParams params = (GridLayout.LayoutParams) card.getLayoutParams();
            params.width = idealW;
            card.setLayoutParams(params);
        }
    }*/

    public void navigate(String category) {
        MainNavigator main = (MainNavigator) getFragmentManager().findFragmentByTag("mainNavigator");
        //String category = (String) view.getTag();
        BackHandlerFragment fragment = SubSectionFragment.newInstance(category);
        getFragmentManager().beginTransaction().
                setCustomAnimations(R.transition.fade_in, R.transition.fade_out, R.transition.fade_in, R.transition.fade_out)
                .hide(main).add(R.id.frame_layout_main,fragment,"subSectionFragment").addToBackStack(null).commit();
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
        getActivity().getFragmentManager().beginTransaction().setCustomAnimations(R.transition.fade_in, R.transition.fade_out, R.transition.fade_in, R.transition.fade_out).add(R.id.linear_layout_bottom, subSectionFragment = SubSectionFragment.newInstance(category), "subSectionFragment").commit();
        isOnSubLevel = true ;
    }

    public void hideSubLevels()
    {
        getActivity().getFragmentManager().beginTransaction().setCustomAnimations(R.transition.fade_in, R.transition.fade_out, R.transition.fade_in, R.transition.fade_out).remove(subSectionFragment).commit();
        mainScrollView.setVisibility(View.VISIBLE);
        ((MainActivity)getActivity()).AnimateMainHeader(null,true);
        isOnSubLevel = false ;
    }
}

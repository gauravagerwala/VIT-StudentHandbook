package vit.vithandbook.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;

import vit.vithandbook.R;

public class MainNavigator extends BackHandlerFragment {

    GridLayout grid;

    public MainNavigator() {

    }

    public static MainNavigator newInstance() {
        MainNavigator frag = new MainNavigator();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_navigator, container, false);
        grid = (GridLayout) view.findViewById(R.id.mainNavGrid);
        // scrollView = (customScrollView)view.findViewById(R.id.mainScrollView);
        // scrollView.setActivity((MainActivity) getActivity());
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
    }

    public void navigate(View view) {
        MainNavigator main = (MainNavigator) getFragmentManager().findFragmentByTag("mainNavigator");
        String category = (String) view.getTag();
        BackHandlerFragment fragment = SubSectionFragment.newInstance(category);
        getFragmentManager().beginTransaction().
                setCustomAnimations(R.transition.fade_in, R.transition.fade_out, R.transition.fade_in, R.transition.fade_out)
                .hide(main).add(R.id.frame_layout_main,fragment,"subSectionFragment").addToBackStack(null).commit();
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }

    int dptopx(int dp) {
        float density = getActivity().getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}

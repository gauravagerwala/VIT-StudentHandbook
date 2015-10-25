package vit.vithandbook.fragment;

<<<<<<< HEAD
import android.app.Fragment;
=======
import android.graphics.Color;
import android.os.Build;
>>>>>>> ui-rehaul
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
<<<<<<< HEAD
import android.widget.ScrollView;
import android.widget.Toast;
=======
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

>>>>>>> ui-rehaul
import vit.vithandbook.R;
import vit.vithandbook.activity.MainActivity;
import vit.vithandbook.adapter.MainNavigatorAdapter;
import vit.vithandbook.adapter.onItemClickListener;

public class MainNavigator extends BackHandlerFragment {

    ScrollView mainScrollView;
    GridLayout grid;
    View rootView;
    RecyclerView mainNavigator ;
    MainNavigatorAdapter rvAdapter ;
    RelativeLayout relativeLayout;
    CollapsingToolbarLayout collapsingToolbarLayout ;

    public MainNavigator() {

    }

    public static MainNavigator newInstance() {
        MainNavigator frag = new MainNavigator();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_navigator, container, false);
        relativeLayout=((MainActivity)getActivity()).relativeLayout;
        collapsingToolbarLayout = ((MainActivity)getActivity()).collapsingToolbarLayout;
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
    public void navigate(String category) {
        MainNavigator main = (MainNavigator) getFragmentManager().findFragmentByTag("mainNavigator");
        /*Snackbar snackbar = Snackbar.make(mainNavigator, "In subSection", Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.DKGRAY);
        snackbar.show();*/
        switch (category){
            case "Academics":
                relativeLayout.setBackground(getResources().getDrawable(R.drawable.head_academics));
                collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.academics));
                collapsingToolbarLayout.setTitle(category);
                if (Build.VERSION.SDK_INT >= 21)
                    getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.academicsDark));
                break;
            case "College":
                relativeLayout.setBackground(getResources().getDrawable(R.drawable.head_college));
                collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.college));
                collapsingToolbarLayout.setTitle(category);
                if (Build.VERSION.SDK_INT >= 21)
                    getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.collegeDark));
                break;
            case "Hostel":
                relativeLayout.setBackground(getResources().getDrawable(R.drawable.head_hostel));
                collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.hostel));
                collapsingToolbarLayout.setTitle(category);
                if (Build.VERSION.SDK_INT >= 21)
                    getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.hostelDark));break;
            case "Life Hacks":
                relativeLayout.setBackground(getResources().getDrawable(R.drawable.head_life_hacks));
                collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.lifehack));
                collapsingToolbarLayout.setTitle(category);
                if (Build.VERSION.SDK_INT >= 21)
                    getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.lifehackDark));break;
            case "Student Organizations":
                relativeLayout.setBackground(getResources().getDrawable(R.drawable.head_student_organizations));
                collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.stud));
                collapsingToolbarLayout.setTitle(category);
                if (Build.VERSION.SDK_INT >= 21)
                    getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.studDark));break;
            case "Around VIT and Vellore":
                relativeLayout.setBackground(getResources().getDrawable(R.drawable.head_around_vit));
                collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.around));
                collapsingToolbarLayout.setTitle(category);
                if (Build.VERSION.SDK_INT >= 21)
                    getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.aroundDark));break;
            default:
                relativeLayout.setBackground(getResources().getDrawable(R.drawable.head_categories));
        }

        //String category = (String) view.getTag();
        BackHandlerFragment fragment = SubSectionFragment.newInstance(category);
        getFragmentManager().beginTransaction().
                setCustomAnimations(R.transition.fade_in, R.transition.fade_out, R.transition.fade_in, R.transition.fade_out)
                .hide(main).add(R.id.frame_layout_main,fragment,"subSectionFragment").addToBackStack(null).commit();
    }

    @Override
    public boolean onBackPressed() {
        relativeLayout.setBackground(getResources().getDrawable(R.drawable.head_categories));
        return false;
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

<<<<<<< HEAD
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
=======
>>>>>>> ui-rehaul
    }
    int dptopx(int dp) {
        float density = dm.density;
        return Math.round((float) dp * density);
    }
<<<<<<< HEAD

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
=======
    */


>>>>>>> ui-rehaul
}

package vit.vithandbook.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import vit.vithandbook.activity.MainActivity;

//Custom view to implement suggestion bar hiding - kinda buggy <- Tanay
public class customScrollView extends ScrollView {

    MainActivity activity;

    public customScrollView(Context context) {
        super(context);

    }

    public customScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public customScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void getActivity(MainActivity act){
        activity = act;
    }


    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        Log.i("SCROLL VIEW", Integer.toString(velocityY));
        if(activity.suggestionContainer.getVisibility() == View.GONE && velocityY < 0) {
            //FOR SWIPE DOWN
            activity.suggestionContainer.setVisibility(View.VISIBLE);

        }
        if(activity.suggestionContainer.getVisibility() == View.VISIBLE && velocityY > 0){
            //FOR SWIPE UP
            activity.suggestionContainer.setVisibility(View.GONE);

        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.i("SCROLL t: ", Integer.toString(t));
        Log.i("SCROLL oldt: ",Integer.toString(oldt));
        if(t == 0)
        {
            //SHOW BAR ONCE IT REACHES TOP
            activity.suggestionContainer.setVisibility(View.VISIBLE);
        }
    }
}

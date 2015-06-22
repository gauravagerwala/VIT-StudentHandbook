package vit.vithandbook.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import vit.vithandbook.activity.MainActivity;

//Custom view to implement suggestion bar hiding - kinda buggy
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

    public void setActivity(MainActivity act){
        activity = act;
    }


    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        //Log.i("SCROLL VIEW", Integer.toString(velocityY));

        //activity.suggestionContainer.animate().translationY(getHeight());
        if(velocityY < 0) {
            //FOR SWIPE UP                                                                          //TODO add transitions
            Log.i("SCROLL VIEW", "swipe up");
            //activity.suggestionContainer.animate().translationY(0).setDuration(70);
            activity.suggestionContainer.setVisibility(View.VISIBLE);

        }
        if(activity.suggestionContainer.getVisibility() == View.VISIBLE && velocityY > 0){
            //FOR SWIPE DOWN
            Log.i("SCROLL VIEW","swipe down");
            //activity.suggestionContainer.animate().translationY(-activity.suggestionContainer.getHeight()).setDuration(70);
            activity.suggestionContainer.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //Log.i("SCROLL t: ", Integer.toString(t));
        //Log.i("SCROLL oldt: ",Integer.toString(oldt));
        if(t == 0)
        {
            //SHOW BAR ONCE IT REACHES TOP
            Log.i("SCROLLCHANGED", "top");
            //activity.suggestionContainer.animate().translationY(0).setDuration(70);               //TODO Add transitions
            activity.suggestionContainer.setVisibility(View.VISIBLE);



        }
    }
}

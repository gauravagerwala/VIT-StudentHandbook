package vit.vithandbook.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;
import vit.vithandbook.fragment.BookmarksFragment;
import vit.vithandbook.fragment.MainNavigator;
import vit.vithandbook.fragment.SubSectionFragment;
import vit.vithandbook.helperClass.FragmentSwitchListener;

/**
 * Created by pulkit on 23/07/2015.
 */
public class MainTabAdapter extends FragmentStatePagerAdapter {

    Context context ;
    FragmentManager fm ;
    public String [] titles = {"Categories","Bookmarks","Contributors"} ;
    Fragment currentFragment = null ;
    public int ITEM_COUNT = 2 ;

    public MainTabAdapter(Context context , FragmentManager fm)
    {
        super(fm);
        this.fm = fm ;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i)
        {
            case 0 :
                if(currentFragment == null)
                {
                    currentFragment = MainNavigator.newInstance(new FragmentSwitchListener() {
                        @Override
                        public void onFragmentSwitch(String category) {
                          fm.beginTransaction().remove(currentFragment).commit();
                          currentFragment = SubSectionFragment.newInstance(category);
                          notifyDataSetChanged();
                        }
                    });
                }
                break ;
            case 1 : currentFragment = BookmarksFragment.newInstance();break;
        }

        return currentFragment ;
    }

    @Override
    public int getItemPosition(Object object)
    {
        if (object instanceof MainNavigator && currentFragment instanceof SubSectionFragment)
            return POSITION_NONE;
        else if (object instanceof SubSectionFragment && currentFragment instanceof MainNavigator)
            return POSITION_NONE ;
        return POSITION_UNCHANGED;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}




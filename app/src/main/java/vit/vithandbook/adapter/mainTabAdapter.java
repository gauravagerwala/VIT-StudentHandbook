package vit.vithandbook.adapter;

import android.content.Context;
import android.app.Fragment;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.app.FragmentManager ;
import vit.vithandbook.fragment.BookmarksFragment;
import vit.vithandbook.fragment.MainNavigator;

/**
 * Created by pulkit on 22/07/2015.
 */
public class mainTabAdapter extends FragmentStatePagerAdapter {

    public String [] headings = {"Categories" , "Bookmarks " , "Contributors"} ;
    public FragmentManager fragmentManager ;
    public int TAB_COUNT = 2 ;
    public Fragment currentFragment ;
    public Context context ;

    public mainTabAdapter(FragmentManager fm , Context context)
    {
        super(fm);
        fragmentManager = fm ;
        this.context = context ;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0 : currentFragment = MainNavigator.newInstance();break;
            case 1 : currentFragment = BookmarksFragment.newInstance();break;

        }
        return currentFragment ;
    }

    @Override
    public int getCount() { return TAB_COUNT ; }

    @Override
    public CharSequence getPageTitle(int position) {
       return headings[position] ;
    }
}

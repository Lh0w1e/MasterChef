package version2.masterchef.com.masterchef.Adapters;

import android.support.v4.app.FragmentStatePagerAdapter;

import version2.masterchef.com.masterchef.Constants.Constants;
import version2.masterchef.com.masterchef.Fragments.FragmentAppetizer;
import version2.masterchef.com.masterchef.Fragments.FragmentDessert;
import version2.masterchef.com.masterchef.Fragments.FragmentFried;
import version2.masterchef.com.masterchef.Fragments.FragmentSoup;
import version2.masterchef.com.masterchef.Fragments.FragmentStew;

/**
 * Created by Colinares on 10/6/2017.
 */
public class TabLayoutAdapter extends FragmentStatePagerAdapter {

    public TabLayoutAdapter(android.support.v4.app.FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        android.support.v4.app.Fragment fragment = null;
        if (position == 0) {
            fragment = new FragmentAppetizer();
        }
        if (position == 1) {
            fragment = new FragmentStew();
        }
        if (position == 2) {
            fragment = new FragmentFried();
        }
        if (position == 3) {
            fragment = new FragmentSoup();
        }
        if (position == 4) {
            fragment = new FragmentDessert();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String name = null;
        if (position == 0) {
            name = Constants.MENU_APPETIZER;
        }
        if (position == 1) {
            name = Constants.MENU_STEW;
        }
        if (position == 2) {
            name = Constants.MENU_FRIED;
        }
        if (position == 3) {
            name = Constants.MENU_SOUP;
        }
        if (position == 4) {
            name = Constants.MENU_DESSERT;
        }

        return name;
    }
}


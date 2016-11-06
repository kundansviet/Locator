package com.aw.locationtracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by kundan on 10/22/2016.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(MainActivity mainActivity, FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment=new NearByPlaces();
                break;
            case 1:
                fragment=new DistanceFinder();
                break;
            case 2:
                fragment=new RouteFinder();
                break;
            case 3:
                fragment=new AddGeoFence();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}

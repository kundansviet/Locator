package com.aw.locationtracker;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LocationFinderCallback {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private LocationFinder locationFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationFinder = new LocationFinder(this);
        viewPager = (ViewPager) findViewById(R.id.pager_home);
        tabLayout = (TabLayout) findViewById(R.id.tab_home);
        PagerAdapter homePagerAdapter = new PagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(homePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.nearby).setText("NEARBY");
        tabLayout.getTabAt(1).setIcon(R.drawable.distance).setText("DISTANCE FINDER");
        tabLayout.getTabAt(2).setIcon(R.drawable.route).setText("ROUTE FINDER");
        tabLayout.getTabAt(3).setIcon(R.drawable.notification).setText("NOTIFIER");



    }

    @Override
    public void locationFound() {

    }
}

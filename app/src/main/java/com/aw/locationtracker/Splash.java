package com.aw.locationtracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Splash extends AppCompatActivity implements LocationFinderCallback {
    private boolean isGpsOn;
    private int timeout = 5000;
    RelativeLayout root_layout;
    View.OnClickListener mOnClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        root_layout= (RelativeLayout) findViewById(R.id.root_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(viewIntent);
            }
        };
        LocationManager manager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        isGpsOn = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGpsOn) {
            LocationFinder locationFinder = new LocationFinder(this);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainscreen = new Intent(Splash.this, MainActivity.class);
                    startActivity(mainscreen);
                }
            }, timeout);
        } else {
            Snackbar snackbar = Snackbar
                    .make(root_layout, "Turn on gps",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Setting", mOnClickListener);
            snackbar.setActionTextColor(Color.RED);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.DKGRAY);
            snackbar.show();
        }


    }

    @Override
    public void locationFound() {
        /*new Handler().post(new Runnable() {
            @Override
            public void run() {
                Intent mainscreen = new Intent(Splash.this, MainActivity.class);
                startActivity(mainscreen);
            }
        });*/
    }
}

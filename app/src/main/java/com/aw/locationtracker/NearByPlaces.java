package com.aw.locationtracker;


import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import static android.R.attr.radius;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class NearByPlaces extends Fragment implements OnMapReadyCallback,LocationFinderCallback{

    private GoogleMap mMap;
    private LocationFinder mlocationFinder;
    private FloatingActionButton fb_search;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    int PLACE_PICKER_REQUEST = 1;
    public NearByPlaces() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_near_by_places, container, false);
        fb_search= (FloatingActionButton) view.findViewById(R.id.fb_search);
        fb_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }*/

                LatLng latLng=new LatLng(LocationFinder.c_lat,LocationFinder.c_lon);

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                builder.setLatLngBounds(toBounds(latLng));
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentByTag("mapFragment");
        if (mapFragment == null) {
            mapFragment = new SupportMapFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.mapFragmentContainer, mapFragment, "mapFragment");
            ft.commit();
            fm.executePendingTransactions();
        }
        mapFragment.getMapAsync(this);

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mlocationFinder=new LocationFinder(getActivity());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng cLocation = new LatLng(LocationFinder.c_lat, LocationFinder.c_lon);
        mMap.addMarker(new MarkerOptions().position(cLocation).title("Marker at current location"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(cLocation).zoom(14).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
       // mMap.setMyLocationEnabled(true);



    }


    @Override
    public void locationFound() {
        LatLng cLocation = new LatLng(LocationFinder.c_lat, LocationFinder.c_lon);
        mMap.addMarker(new MarkerOptions().position(cLocation).title("Marker at current location"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(cLocation).zoom(14).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        mMap.setMyLocationEnabled(true);
    }

    public LatLngBounds toBounds(LatLng center) {
        LatLng southwest = SphericalUtil.computeOffset(center, 1000* Math.sqrt(2.0), 225);
        LatLng northeast = SphericalUtil.computeOffset(center, 1000* Math.sqrt(2.0), 45);
        return new LatLngBounds(southwest, northeast);
    }
}

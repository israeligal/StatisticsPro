package com.example.shaha.mepo.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.shaha.mepo.R;
import com.example.shaha.mepo.Utils.LocationHelper;
import com.example.shaha.mepo.Utils.MapUtils;
import com.example.shaha.mepo.Utils.PermissionUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * This is the map fragment of the app
 */
public class LocalEventsFragment extends Fragment implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,FragmentCompat.OnRequestPermissionsResultCallback {
    private GoogleMap mMap;
    private boolean mapReady = false;
    private Location mLastLocation;
    private LocationHelper locationHelper;

    public LocalEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //set the mapFragment to call the mapReady callback when the map is loaded
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button mapBtn = (Button) view.findViewById(R.id.map_map_btn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapReady) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });
        Button satelliteBtn = (Button) view.findViewById(R.id.map_satellite_btn);
        satelliteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapReady) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
            }
        });
        Button hybridBtn = (Button) view.findViewById(R.id.map_hybrid_btn);
        hybridBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapReady) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }
            }
        });

        //get the current location
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        locationHelper = new LocationHelper(getContext());
        locationHelper.checkpermission();

        // check availability of play services
        if (locationHelper.checkPlayServices()) {

            // Building the GoogleApi client
            //locationHelper.buildGoogleApiClient();
            //mLastLocation = locationHelper.getLocation();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady = true;
        mMap = googleMap;
        MapUtils.setEventMarkers(mMap);
    }

    private void moveToLocation(Location loc) {
        LatLng curPosition = new LatLng(loc.getLatitude(),loc.getLongitude());
        CameraPosition target = CameraPosition.builder().target(curPosition).zoom(14).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("Connection failed:", " ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        mLastLocation=locationHelper.getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        locationHelper.connectApiClient();
    }


    // Permission check functions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        locationHelper.onRequestPermissionsResult(requestCode,permissions,grantResults);

    }

    @Override
    public void onResume() {
        super.onResume();
        locationHelper.checkPlayServices();
    }
}

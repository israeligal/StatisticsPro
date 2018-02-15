package com.example.shaha.mepo.Utils;

import android.content.Context;

import com.example.shaha.mepo.Fragments.PersonalAreaFragment;
import com.example.shaha.mepo.Manifest;
import com.example.shaha.mepo.MepoCoordinate;
import com.example.shaha.mepo.MepoEvent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by shaha on 14/11/2017.
 */

public class MapUtils {
    private static GoogleMap mMap;

    public static void setEventMarkers(GoogleMap map){
        mMap = map;
        ArrayList<MepoEvent> events = MepoEventUtils.getEvents();
        for(MepoEvent event:events){
            double latitude = event.getEventLocation().getMepoCoordinate().getLatitude();
            double longtitude = event.getEventLocation().getMepoCoordinate().getLongtitude();
            setMarker(latitude,longtitude,event.getEventName());
        }
    }

    private static void setMarker(double latitude, double longtitude,String eventName) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longtitude)).title(eventName));
    }
}

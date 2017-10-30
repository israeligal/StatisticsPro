package com.example.shaha.mepo;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by shaha on 30/10/2017.
 */

public class Location {
    private String locationName;
    private String locationAddress;
    //private Uri locationWebsite;
    //private LatLng locationLatLng;

    public Location(){

    }

    public Location(String locationName, String locationAddress){
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        //this.locationWebsite = locationWebsite;
        //this.locationLatLng = locationLatLng;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

//    public Uri getLocationWebsite() {
//        return locationWebsite;
//    }
//
//    public void setLocationWebsite(Uri locationWebsite) {
//        this.locationWebsite = locationWebsite;
//    }
//
//    public LatLng getLocationLatLng() {
//        return locationLatLng;
//    }
//
//    public void setLocationLatLng(LatLng locationLatLng) {
//        this.locationLatLng = locationLatLng;
//    }
}

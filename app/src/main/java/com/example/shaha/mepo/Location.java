package com.example.shaha.mepo;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by shaha on 30/10/2017.
 */

public class Location implements Parcelable{
    private String locationName;
    private String locationAddress;
    private Coordinate coordinate;

    public Location(){

    }

    public Location(String locationName, String locationAddress, Coordinate coordinate){
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.coordinate = coordinate;
    }

    protected Location(Parcel in) {
        locationName = in.readString();
        locationAddress = in.readString();
        coordinate = (Coordinate)in.readParcelable(Coordinate.class.getClassLoader());
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

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(locationName);
        parcel.writeString(locationAddress);
        parcel.writeParcelable(coordinate,1);
    }
}

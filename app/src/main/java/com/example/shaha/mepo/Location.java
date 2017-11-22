package com.example.shaha.mepo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaha on 30/10/2017.
 */

public class Location implements Parcelable{
    private String locationName;
    private String locationAddress;
    private MepoCoordinate mepoCoordinate;

    public Location(){

    }

    public Location(String locationName, String locationAddress, MepoCoordinate mepoCoordinate){
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.mepoCoordinate = mepoCoordinate;
    }

    protected Location(Parcel in) {
        locationName = in.readString();
        locationAddress = in.readString();
        mepoCoordinate = (MepoCoordinate)in.readParcelable(MepoCoordinate.class.getClassLoader());
    }

    public MepoCoordinate getMepoCoordinate() {
        return mepoCoordinate;
    }

    public void setMepoCoordinate(MepoCoordinate mepoCoordinate) {
        this.mepoCoordinate = mepoCoordinate;
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
        parcel.writeParcelable(mepoCoordinate,1);
    }
}

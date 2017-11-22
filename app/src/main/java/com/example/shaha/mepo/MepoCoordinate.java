package com.example.shaha.mepo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaha on 03/11/2017.
 */

public class MepoCoordinate implements Parcelable{
    private double latitude;
    private double longtitude;

    public MepoCoordinate(){
        //An empty constructor
        //this is crucial for the app to load from firebase
    }

    public MepoCoordinate(double latitude, double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    protected MepoCoordinate(Parcel in) {
        latitude = in.readDouble();
        longtitude = in.readDouble();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public static final Creator<MepoCoordinate> CREATOR = new Creator<MepoCoordinate>() {
        @Override
        public MepoCoordinate createFromParcel(Parcel in) {
            return new MepoCoordinate(in);
        }

        @Override
        public MepoCoordinate[] newArray(int size) {
            return new MepoCoordinate[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(latitude);
        parcel.writeDouble(longtitude);
    }
}

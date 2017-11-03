package com.example.shaha.mepo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaha on 03/11/2017.
 */

class Coordinate implements Parcelable{
    private double latitude;

    private double longtitude;
    public Coordinate(){
        //An empty constructor
        //this is crucial for the app to load from firebase
    }

    public Coordinate(double latitude, double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    protected Coordinate(Parcel in) {
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

    public static final Creator<Coordinate> CREATOR = new Creator<Coordinate>() {
        @Override
        public Coordinate createFromParcel(Parcel in) {
            return new Coordinate(in);
        }

        @Override
        public Coordinate[] newArray(int size) {
            return new Coordinate[size];
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

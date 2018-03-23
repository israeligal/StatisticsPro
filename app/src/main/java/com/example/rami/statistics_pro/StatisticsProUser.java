package com.example.rami.statistics_pro;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;



public class StatisticsProUser implements Parcelable {
    private String displayName;
    private String email;
    private String phoneNumber;


    public StatisticsProUser() {
    }

    public StatisticsProUser(String displayName, String email, String phoneNumber) {
        this.displayName = displayName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public StatisticsProUser(FirebaseUser user) {
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
    }

    protected StatisticsProUser(Parcel in) {
        displayName = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
    }


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static final Creator<StatisticsProUser> CREATOR = new Creator<StatisticsProUser>() {
        @Override
        public StatisticsProUser createFromParcel(Parcel in) {
            return new StatisticsProUser(in);
        }

        @Override
        public StatisticsProUser[] newArray(int size) {
            return new StatisticsProUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeString(email);
        dest.writeString(phoneNumber);
    }


}

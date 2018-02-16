package com.example.shaha.mepo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by shaha on 06/11/2017.
 */

public class MepoUser implements Parcelable {
    private String displayName;
    private String email;
    private String phoneNumber;


    public MepoUser() {
    }

    public MepoUser(String displayName, String email, String phoneNumber) {
        this.displayName = displayName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public MepoUser(FirebaseUser user) {
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
    }

    protected MepoUser(Parcel in) {
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

    public static final Creator<MepoUser> CREATOR = new Creator<MepoUser>() {
        @Override
        public MepoUser createFromParcel(Parcel in) {
            return new MepoUser(in);
        }

        @Override
        public MepoUser[] newArray(int size) {
            return new MepoUser[size];
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

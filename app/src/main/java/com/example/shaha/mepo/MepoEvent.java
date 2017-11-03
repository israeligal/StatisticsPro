package com.example.shaha.mepo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.location.places.Place;

import java.util.Date;

/**
 * Created by shaha on 17/10/2017.
 */

public class MepoEvent implements Parcelable {
    String eventName;
    String lastMessage;
    String description;
    Location eventPlace;
    Date startTime;
    Date endTime;

    public MepoEvent(){
        //The default constructor
    }

    public MepoEvent(String eventName, String type, Date startTime, Date endTime, Location eventPlace){
        this.eventName = eventName;
        this.description = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lastMessage = "";
        this.eventPlace = eventPlace;
    }

    protected MepoEvent(Parcel in) {
        eventName = in.readString();
        lastMessage = in.readString();
        description = in.readString();
        eventPlace = (Location)in.readParcelable(Location.class.getClassLoader());
        startTime = new Date(in.readLong());
        endTime = new Date(in.readLong());
    }

    public static final Creator<MepoEvent> CREATOR = new Creator<MepoEvent>() {
        @Override
        public MepoEvent createFromParcel(Parcel in) {
            return new MepoEvent(in);
        }

        @Override
        public MepoEvent[] newArray(int size) {
            return new MepoEvent[size];
        }
    };

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getAddress() {return eventPlace;}

    public void setAddress(Location eventPlace) {this.eventPlace = eventPlace;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(eventName);
        parcel.writeString(lastMessage);
        parcel.writeString(description);
        parcel.writeParcelable(eventPlace,1);
        parcel.writeLong(startTime.getTime());
        parcel.writeLong(endTime.getTime());
    }
}

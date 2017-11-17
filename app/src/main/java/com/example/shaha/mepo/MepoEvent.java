package com.example.shaha.mepo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by shaha on 17/10/2017.
 */

public class MepoEvent implements Parcelable {
    final static int EVENT_NAME = 0;
    final static int EVENT_ADDRESS = 1;
    final static int EVENT_TYPE = 2;
    final static int EVENT_START_TIME = 3;
    final static int EVENT_END_TIME = 4;
    final static int EVENT_ACTIVE_USER = 5;
    private String eventId;
    private String eventName;
    private String lastMessage;
    private String description;
    private Location eventPlace;
    private Date startTime;
    private Date endTime;
    private MepoUser eventHost;
    // Using for tests
    public MepoEvent(){
        this.eventName = "NewEvent";
        this.description = "Sports";
        Date start = new Date();
        this.startTime = start;
        this.endTime = start;
        this.lastMessage = "";
        Location loc = new Location("asd","asd",new Coordinate(1.2,1.3));
        this.eventPlace = loc;
        MepoUser ss = new MepoUser("Gal","email@email","0509919999");
        this.eventHost = ss;
    }

    public MepoEvent(String eventName, String type, Date startTime, Date endTime, Location eventPlace, MepoUser eventHost){
        this.eventName = eventName;
        this.description = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lastMessage = "";
        this.eventPlace = eventPlace;
        this.eventHost = eventHost;
    }
    public MepoEvent(ArrayList<Object> eventData){
        this(eventData.get(EVENT_NAME).toString()
                , eventData.get(EVENT_TYPE).toString(),
                (Date) eventData.get(EVENT_START_TIME),
                (Date) eventData.get(EVENT_END_TIME),
                (Location) eventData.get(EVENT_ADDRESS),
                (MepoUser) eventData.get(EVENT_ACTIVE_USER));
    }

    protected MepoEvent(Parcel in) {
        eventId = in.readString();
        eventName = in.readString();
        lastMessage = in.readString();
        description = in.readString();
        eventPlace = (Location)in.readParcelable(Location.class.getClassLoader());
        startTime = new Date(in.readLong());
        endTime = new Date(in.readLong());
        eventHost = (MepoUser)in.readParcelable(MepoUser.class.getClassLoader());
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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

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

    public String getAddress() {

        return eventPlace.getLocationAddress();
    }

    public void setAddress(Location eventPlace) {this.eventPlace = eventPlace;}

    public MepoUser getEventHost() {
        return eventHost;
    }

    public void setEventHost(MepoUser eventHost) {
        this.eventHost = eventHost;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(eventId);
        parcel.writeString(eventName);
        parcel.writeString(lastMessage);
        parcel.writeString(description);
        parcel.writeParcelable(eventPlace,1);
        parcel.writeLong(startTime.getTime());
        parcel.writeLong(endTime.getTime());
        parcel.writeParcelable(eventHost,1);
    }
}

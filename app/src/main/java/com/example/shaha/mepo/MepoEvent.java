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
    final static int EVENT_LOCATION = 1;
    final static int EVENT_TYPE = 2;
    final static int EVENT_START_TIME = 3;
    final static int EVENT_END_TIME = 4;
    final static int EVENT_ACTIVE_USER = 5;
    private String eventFireBaseId;
    private String eventName;
    private String lastMessage;
    private String description;
    private Location eventLocation;
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
        Location loc = new Location("asd","asd",new MepoCoordinate(1.2,1.3));
        this.eventLocation = loc;
        MepoUser ss = new MepoUser("Gal","email@email","0509919999");
        this.eventHost = ss;
    }

    public MepoEvent(String eventName, String type, Date startTime, Date endTime, Location eventLocation,
                     MepoUser eventHost,String eventFireBaseId){
        this.eventName = eventName;
        this.description = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lastMessage = "";
        this.eventLocation = eventLocation;
        this.eventHost = eventHost;
        this.eventFireBaseId = eventFireBaseId;
    }
    public MepoEvent(ArrayList<Object> eventData,String eventFireBaseID){
        this(eventData.get(EVENT_NAME).toString()
                , eventData.get(EVENT_TYPE).toString(),
                (Date) eventData.get(EVENT_START_TIME),
                (Date) eventData.get(EVENT_END_TIME),
                (Location) eventData.get(EVENT_LOCATION),
                (MepoUser) eventData.get(EVENT_ACTIVE_USER),eventFireBaseID);
    }

    protected MepoEvent(Parcel in) {
        eventFireBaseId = in.readString();
        eventName = in.readString();
        lastMessage = in.readString();
        description = in.readString();
        eventLocation = (Location)in.readParcelable(Location.class.getClassLoader());
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

    public String getEventFireBaseId() {
        return eventFireBaseId;
    }

//    public void setEventFireBaseId(String eventFireBaseId) {
//        this.eventFireBaseId = eventFireBaseId;
//    }

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

    public Location getEventLocation() {

        return eventLocation;
    }

    public void setAddress(Location eventLocation) {this.eventLocation = eventLocation;}

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
        parcel.writeString(eventFireBaseId);
        parcel.writeString(eventName);
        parcel.writeString(lastMessage);
        parcel.writeString(description);
        parcel.writeParcelable(eventLocation,1);
        parcel.writeLong(startTime.getTime());
        parcel.writeLong(endTime.getTime());
        parcel.writeParcelable(eventHost,1);
    }
}

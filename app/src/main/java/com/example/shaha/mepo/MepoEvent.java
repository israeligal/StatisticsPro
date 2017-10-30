package com.example.shaha.mepo;

import com.google.android.gms.location.places.Place;

import java.util.Date;

/**
 * Created by shaha on 17/10/2017.
 */

public class MepoEvent {
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
}

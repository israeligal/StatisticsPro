package com.example.shaha.mepo;

import java.util.Date;

/**
 * Created by shaha on 17/10/2017.
 */

public class MepoEvent {
    String eventName;
    String lastMessage;
    String description;
    String address;
    Date startTime;
    Date endTime;

    public MepoEvent(){
        //The default constructor
    }

    public MepoEvent(String eventName, String type, Date startTime, Date endTime, String address){
        this.eventName = eventName;
        this.description = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lastMessage = "";
        this.address = address;
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

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}
}

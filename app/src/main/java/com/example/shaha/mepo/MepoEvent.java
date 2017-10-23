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
    String eventTime;
    public MepoEvent(){

    }
    /**
     * The constructor
     * @param eventName
     * @param type
     * @param eventTime
     */
    public MepoEvent(String eventName, String type, String eventTime, String address){
        this.eventName = eventName;
        this.description = type;
        this.eventTime = eventTime;
        this.lastMessage = "";
        this.address = address;
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

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}
}

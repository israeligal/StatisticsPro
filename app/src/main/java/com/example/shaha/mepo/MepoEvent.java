package com.example.shaha.mepo;

import java.util.Date;

/**
 * Created by shaha on 17/10/2017.
 */

public class MepoEvent {
    String eventName;
    String lastMessage;
    String description;
    Date eventTime;

    public MepoEvent(String eventName, String description, Date eventTime){
        this.eventName = eventName;
        this.description = description;
        this.eventTime = eventTime;
        this.lastMessage = "";
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

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }
}

package com.example.shaha.mepo.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.widget.BaseAdapter;

import com.example.shaha.mepo.MepoEvent;
import com.example.shaha.mepo.data.MepoContracts.EventsEntry;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class MepoEventUtils {

    private static DatabaseReference mDatabaseReference;
    private static ArrayList<MepoEvent> mMepoEvents;
    private static Uri SQL_EVENTS_DB = EventsEntry.CONTENT_URI;
    static {
        mDatabaseReference = FirebaseUtils.getFirebaseReference("Events");
        mMepoEvents = new ArrayList<>();
    }

    // TODO is it ok to make context final?
    public static void addChildListener(final BaseAdapter listAdaper, final Context context) {
        ChildEventListener mChildEventListener = new ChildEventListener() {
            //Handle a new event
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MepoEvent newMepoEvent = dataSnapshot.getValue(MepoEvent.class);
                if( newMepoEvent != null && !mMepoEvents.contains(newMepoEvent)) {
                    mMepoEvents.add(newMepoEvent);
                    if(context != null){
                        insertNewEvent(newMepoEvent, context);

                    }
                    listAdaper.notifyDataSetChanged();
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MepoEvent newMepoEvent = dataSnapshot.getValue(MepoEvent.class);
                //try to remove from arrayList
                boolean removed = mMepoEvents.remove(newMepoEvent);
                //if removed from list than remove from data base also if needed
                if(removed){
                    String where = "newMepoEvent.getEventId()=?";
                    String[] selectionArgs = {newMepoEvent.getEventId()};
                    context.getContentResolver().delete(EventsEntry.CONTENT_URI,where,selectionArgs);
                }
                listAdaper.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }

    public static ArrayList<MepoEvent> getEvents(){
        return mMepoEvents;
    }

    public static void insertNewEvent(MepoEvent newMepoEvent,Context context){
        //insert Mepo event to sql database, generate list of content values to insert
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventsEntry.COLUMN_EVENT_NAME, newMepoEvent.getEventName());
        contentValues.put(EventsEntry.COLUMN_EVENT_HOST_EMAIL,  newMepoEvent.getEventHost().getEmail());
        contentValues.put(EventsEntry.COLUMN_EVENT_LOCATION,  newMepoEvent.getAddress());
        contentValues.put(EventsEntry.COLUMN_EVENT_TYPE,  newMepoEvent.getDescription());
        contentValues.put(EventsEntry.COLUMN_EVENT_START,  newMepoEvent.getStartTime().getTime());
        contentValues.put(EventsEntry.COLUMN_EVENT_END,  newMepoEvent.getEndTime().getTime());
        //Call insert method from Mepo provider
//        context.getContentResolver().insert(SQL_EVENTS_DB,contentValues);

    }

}

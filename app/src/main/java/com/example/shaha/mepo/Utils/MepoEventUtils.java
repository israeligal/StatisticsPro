package com.example.shaha.mepo.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.shaha.mepo.Adapters.EventsListAdapter;
import com.example.shaha.mepo.MainActivity;
import com.example.shaha.mepo.MepoEvent;
import com.example.shaha.mepo.data.MepoContracts.EventsEntry;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class MepoEventUtils {

    private static DatabaseReference mDatabaseReference;
    private static ArrayList<MepoEvent> mMepoEvents;
    private static EventsListAdapter mAllEventsListAdapter;
    private static Uri SQL_EVENTS_DB = EventsEntry.CONTENT_URI;
    private static String mCurrentMepoUserEmail;
    private static ChildEventListener mChildEventListener;
    private static String LOG_TAG = MepoEventUtils.class.getName();
    /**
     clears and sets an arrayAdapter to MyMepoEvents(Personal Area)
     **/

    public static void addDatabaseListener(final Context context) {
        if (mChildEventListener != null){
            return;
        }
        mDatabaseReference = FirebaseUtils.getFirebaseReference("Events");
        mMepoEvents = new ArrayList<>();
        mAllEventsListAdapter = new EventsListAdapter(context,mMepoEvents);
        if ( MainActivity.getCurrentUser() != null){
            mCurrentMepoUserEmail = MainActivity.getCurrentUser().getEmail();
        }
         mChildEventListener = new ChildEventListener() {
            //Handle a new event
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MepoEvent newMepoEvent = dataSnapshot.getValue(MepoEvent.class);
                if( newMepoEvent == null){
                    Log.e(LOG_TAG,"Could not remove event");
                    return;
                }
                if(!mMepoEvents.contains(newMepoEvent)) {
                    mAllEventsListAdapter.add(newMepoEvent);
                    if(mCurrentMepoUserEmail != null && mCurrentMepoUserEmail.equals(newMepoEvent.getEventHost().getEmail())){
                        context.getContentResolver().insert(SQL_EVENTS_DB, mepoEventToContentValues(newMepoEvent));
                    }
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MepoEvent newMepoEvent = dataSnapshot.getValue(MepoEvent.class);
                //try to remove from arrayList
                if(newMepoEvent == null){
                    Log.e(LOG_TAG,"Could not remove event");
                    return;
                }
                boolean removed = mMepoEvents.remove(newMepoEvent);
                if(mCurrentMepoUserEmail.equals(newMepoEvent.getEventHost().getEmail())){
                    String[] eventId ={newMepoEvent.getEventFireBaseId()} ;
                    String where = EventsEntry.COLUMN_EVENT_FIREBASE_ID + "=?";
                    context.getContentResolver().delete(EventsEntry.CONTENT_URI,where,eventId );
                }
                //if removed from list than remove from data base also if needed
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(LOG_TAG,"Database Error\n" + databaseError.getMessage());
//                throw new RuntimeException(databaseError.getMessage());
            }

        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }


    public static ArrayList<MepoEvent> getEvents(){
        return mMepoEvents;
    }

    public static EventsListAdapter getmAllEventsListAdapter() {
        return mAllEventsListAdapter;
    }

    public static void setmCurrentMepoUserEmail(String mCurrentMepoUserEmail) {
        MepoEventUtils.mCurrentMepoUserEmail = mCurrentMepoUserEmail;
    }

    public static ContentValues mepoEventToContentValues(MepoEvent newMepoEvent){
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventsEntry.COLUMN_EVENT_NAME, newMepoEvent.getEventName());
        contentValues.put(EventsEntry.COLUMN_EVENT_HOST_EMAIL,  newMepoEvent.getEventHost().getEmail());
        contentValues.put(EventsEntry.COLUMN_EVENT_LOCATION,  newMepoEvent.getEventLocation().getLocationAddress());
        contentValues.put(EventsEntry.COLUMN_EVENT_FIREBASE_ID,  newMepoEvent.getEventFireBaseId());
        contentValues.put(EventsEntry.COLUMN_EVENT_LAST_MESSAGE,  newMepoEvent.getLastMessage());
        contentValues.put(EventsEntry.COLUMN_EVENT_TYPE,  newMepoEvent.getDescription());
        contentValues.put(EventsEntry.COLUMN_EVENT_START,  newMepoEvent.getStartTime().getTime());
        contentValues.put(EventsEntry.COLUMN_EVENT_END,  newMepoEvent.getEndTime().getTime());
        return contentValues;
    }


}

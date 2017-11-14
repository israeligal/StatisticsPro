package com.example.shaha.mepo.Utils;

import com.example.shaha.mepo.Adapters.EventsListAdapter;
import com.example.shaha.mepo.MepoEvent;
import com.example.shaha.mepo.MepoEventListView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MepoEventUtils {

    private static DatabaseReference mDatabaseReference;
    private static ArrayList<MepoEvent> mMepoEvents;

    static {
        mDatabaseReference = FirebaseUtils.getFirebaseReference("Events");
        mMepoEvents = new ArrayList<>();
    }

    public static void addChildListener(final EventsListAdapter listAdaper) {

        ChildEventListener mChildEventListener = new ChildEventListener() {
            //Handle a new event
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MepoEvent newMepoEvent = dataSnapshot.getValue(MepoEvent.class);
                if(!mMepoEvents.contains(newMepoEvent)) {
                    mMepoEvents.add(newMepoEvent);
                }
                //listAdaper.add(newMepoEvent);
                listAdaper.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MepoEvent newMepoEvent = dataSnapshot.getValue(MepoEvent.class);
                mMepoEvents.add(newMepoEvent);
                //listAdaper.remove(newMepoEvent);
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
}

package com.example.shaha.mepo.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.shaha.mepo.Adapters.EventsListAdapter;
import com.example.shaha.mepo.AddEventActivity;
import com.example.shaha.mepo.EventInfoPopUpActivity;
import com.example.shaha.mepo.MainActivity;
import com.example.shaha.mepo.MepoEvent;
import com.example.shaha.mepo.MepoEventListView;
import com.example.shaha.mepo.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PersonalAreaFragment extends Fragment  {

    private List<MepoEvent> events;

    public PersonalAreaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_area, container, false);

        //create a dummy arrayList of events
        events = new ArrayList<>();

        //set the list view
        ListView eventsListView = (ListView)view.findViewById(R.id.my_events_list_view);
        MepoEventListView mepoEventListView = new MepoEventListView(eventsListView, getContext());

        FloatingActionButton addEvent = (FloatingActionButton)view.findViewById(R.id.fab);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddEvent = new Intent(getContext(),AddEventActivity.class);
                startActivity(intentAddEvent);
            }
        });

        return view;
    }

}

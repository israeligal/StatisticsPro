package com.example.shaha.mepo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PersonalAreaFragment extends Fragment {


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
        List<MepoEvent> events = new ArrayList<>();
        events.add(new MepoEvent("event1","blabla",null));
        events.add(new MepoEvent("event2","blabla",null));
        events.add(new MepoEvent("event11","blabla",null));
        events.add(new MepoEvent("event14","blabla",null));

        //set the list view
        ListView eventsListView = (ListView)view.findViewById(R.id.my_events_list_view);
        EventsListAdapter listAdapter = new EventsListAdapter(getContext(),events);
        eventsListView.setAdapter(listAdapter);

        //
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

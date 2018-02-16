package com.example.shaha.mepo.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.shaha.mepo.Adapters.EventsListAdapter;
import com.example.shaha.mepo.AddEventActivity;
import com.example.shaha.mepo.MepoEvent;
import com.example.shaha.mepo.MepoEventListView;
import com.example.shaha.mepo.R;

import java.util.List;

public class PersonalAreaFragment extends Fragment {

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

        //set the list view
        ListView eventsListView = (ListView) view.findViewById(R.id.my_events_list_view);
        MepoEventListView mepoEventListView = new MepoEventListView(eventsListView, getContext());
        EventsListAdapter listAdapter = mepoEventListView.getmListAdapter();

        //Set floating action button listener to add event
        FloatingActionButton addEvent = (FloatingActionButton) view.findViewById(R.id.fab);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddEvent = new Intent(getContext(), AddEventActivity.class);
                startActivity(intentAddEvent);
            }
        });

        //set an empty state for the listview
        LinearLayout emptyLayout = (LinearLayout) view.findViewById(R.id.personal_empty_state);
        eventsListView.setEmptyView(emptyLayout);

        return view;
    }

}

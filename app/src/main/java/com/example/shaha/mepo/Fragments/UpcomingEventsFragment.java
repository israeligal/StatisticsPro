package com.example.shaha.mepo.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.shaha.mepo.Adapters.EventsListAdapter;
import com.example.shaha.mepo.AddEventActivity;
import com.example.shaha.mepo.MepoEventListView;
import com.example.shaha.mepo.PersonalFragmentListView;
import com.example.shaha.mepo.R;

public class UpcomingEventsFragment extends Fragment {
    private static int ALL_EVENTS_DB_LOADER_ID = 2;
    private EventsListAdapter mEventsListAdapter;

    public UpcomingEventsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_upcoming_events, container, false);
//        getActivity().getSupportLoaderManager().initLoader(ALL_EVENTS_DB_LOADER_ID, null, this);
//        set the list view
        ListView allEventsListView = (ListView)view.findViewById(R.id.upcoming_events_list_view);
        MepoEventListView mepoEventListView = new MepoEventListView(allEventsListView, getContext());
        mEventsListAdapter = mepoEventListView.getmListAdapter();

        return view;
    }
}

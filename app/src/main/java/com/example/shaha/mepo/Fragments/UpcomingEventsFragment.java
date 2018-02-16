package com.example.shaha.mepo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.shaha.mepo.Adapters.EventsListAdapter;
import com.example.shaha.mepo.MepoEventListView;
import com.example.shaha.mepo.R;

public class UpcomingEventsFragment extends Fragment {
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

        // set the list view
        ListView allEventsListView = (ListView) view.findViewById(R.id.upcoming_events_list_view);
        MepoEventListView mepoEventListView = new MepoEventListView(allEventsListView, getContext());
        mEventsListAdapter = mepoEventListView.getmListAdapter();

        //set an empty state for the listview
        LinearLayout emptyLayout = (LinearLayout) view.findViewById(R.id.upcoming_empty_state);
        allEventsListView.setEmptyView(emptyLayout);

        return view;
    }
}

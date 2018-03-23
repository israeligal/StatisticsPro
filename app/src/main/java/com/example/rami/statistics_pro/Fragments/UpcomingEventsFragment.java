package com.example.rami.statistics_pro.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.rami.statistics_pro.R;

public class UpcomingEventsFragment extends Fragment {

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
//        StatisticsProEventListView StatisticsProEventListView = new StatisticsProEventListView(allEventsListView, getContext());
//        mEventsListAdapter = StatisticsProEventListView.getmListAdapter();

        //set an empty state for the listview
        LinearLayout emptyLayout = (LinearLayout) view.findViewById(R.id.upcoming_empty_state);
        allEventsListView.setEmptyView(emptyLayout);

        return view;
    }
}

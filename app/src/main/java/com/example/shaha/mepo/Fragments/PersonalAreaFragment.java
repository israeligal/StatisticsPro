package com.example.shaha.mepo.Fragments;

import android.content.ContentValues;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.shaha.mepo.Adapters.MyMepoEventsCursorAdapter;
import com.example.shaha.mepo.AddEventActivity;
import com.example.shaha.mepo.MepoEvent;
import com.example.shaha.mepo.PersonalFragmentListView;
import com.example.shaha.mepo.R;
import com.example.shaha.mepo.data.MepoContracts.EventsEntry;

import java.util.List;

public class PersonalAreaFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private List<MepoEvent> events;
    private MyMepoEventsCursorAdapter myMyMepoEventsCursorAdapter;
    private static int MY_EVENTS_DB_LOADER_ID = 0;
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
        getActivity().getSupportLoaderManager().initLoader(MY_EVENTS_DB_LOADER_ID, null, this);
        //set the list view
        ListView eventsListView = (ListView)view.findViewById(R.id.my_events_list_view);
        PersonalFragmentListView personalFragmentListView = new PersonalFragmentListView(eventsListView, getContext());
        myMyMepoEventsCursorAdapter = personalFragmentListView.getmListAdapter();

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



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                EventsEntry._ID, EventsEntry.COLUMN_EVENT_NAME, EventsEntry.COLUMN_EVENT_HOST_EMAIL
                , EventsEntry.COLUMN_EVENT_LOCATION, EventsEntry.COLUMN_EVENT_TYPE, EventsEntry.COLUMN_EVENT_START,
                EventsEntry.COLUMN_EVENT_END};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(getContext(),   // Parent activity context
                EventsEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        myMyMepoEventsCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        myMyMepoEventsCursorAdapter.swapCursor(null);

    }


}

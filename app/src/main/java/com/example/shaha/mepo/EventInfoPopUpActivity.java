package com.example.shaha.mepo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.DisplayMetrics;
import android.widget.TextView;
import com.example.shaha.mepo.data.MepoContracts.EventsEntry;


public class EventInfoPopUpActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private Uri mEventUri;
    private TextView mTextView;
    private static int EVENT_DB_LOADER_ID = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details_popup);
        int[] width_height = getDimensions();
        //Set popup window to take portion of the screen.
        getWindow().setLayout((int) (width_height[0] * 0.8), (int) (width_height[1] * 0.7));
        MepoEvent curEvent;
        mTextView = (TextView) findViewById(R.id.event_info_event_name_tv);

        //get the MepoEvent object
        Intent i = getIntent();
        if (i.getData() != null) {
            mEventUri = i.getData();
            getSupportLoaderManager().initLoader(EVENT_DB_LOADER_ID, null, this);
        } else {
            curEvent = (MepoEvent) i.getParcelableExtra("event");
            //show the event in the GUI
            updateUI(curEvent);
        }
    }

    private void updateUI(MepoEvent curEvent) {
        TextView eventNameTextView = (TextView) findViewById(R.id.event_info_event_name_tv);
        eventNameTextView.setText(curEvent.getEventName());
    }

    //Get the device dimensions and return width and height
    private int[] getDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // set what data do we want the cursor to load from data base
        String[] projection = {EventsEntry._ID, EventsEntry.COLUMN_EVENT_NAME, EventsEntry.COLUMN_EVENT_HOST_EMAIL
                , EventsEntry.COLUMN_EVENT_LOCATION, EventsEntry.COLUMN_EVENT_TYPE, EventsEntry.COLUMN_EVENT_START,
                EventsEntry.COLUMN_EVENT_END
        };
        // return a Cursor load for a specific event
        return new CursorLoader(this, mEventUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        if (data == null || data.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (data.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int nameColumnIndex = data.getColumnIndex(EventsEntry.COLUMN_EVENT_NAME);

            // Extract out the value from the Cursor for the given column index
            String name = data.getString(nameColumnIndex);

            // Update the views on the screen with the values from the database
            mTextView.setText(name);
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mTextView.setText("");
    }



}

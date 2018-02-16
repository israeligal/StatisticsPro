package com.example.shaha.mepo;

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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class EventInfoPopUpActivity extends FragmentActivity {
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
        curEvent = (MepoEvent) i.getParcelableExtra("event");
        //show the event in the GUI
        updateUI(curEvent);
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
}

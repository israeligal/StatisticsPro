package com.example.shaha.mepo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by Gal on 01/11/2017.
 */

public class EventInfoPopUpActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details_popup);

        int[] width_height = getDimensions();
        //Set popup window to take portion of the screen.
        getWindow().setLayout((int)(width_height[0]*0.8),(int)(width_height[1]*0.7));

        //get the MepoEvent object
        Intent i = getIntent();
        MepoEvent curEvent = (MepoEvent)i.getParcelableExtra("event");

        //show the event in the GUI
        updateUI(curEvent);
    }

    private void updateUI(MepoEvent curEvent) {
        TextView eventNameTextView = (TextView)findViewById(R.id.event_info_event_name_tv);
        eventNameTextView.setText(curEvent.getEventName());
    }

    //Get the device dimensions and return width and height
    private int[] getDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return new int[]{displayMetrics.widthPixels,displayMetrics.heightPixels};
    }
}

package com.example.shaha.mepo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity {
    private static final int RC_PICK_IMAGE = 1;
    String[] options;
    private Calendar calendar;
    private int year, month, day;
    private ImageView addEventImg;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private final int EVENTNAME = 0;
    private final int EVENTADDRESS = 1;
    private final int EVENTTYPE = 2;
    private final int EVENTDATE = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //set up the spinner
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setUpDateBtnListener();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("Events");

        setUpSpinner();
        setUpEventImgListener();
        setUpAddEventBtnListener();
    }

    private void setUpAddEventBtnListener() {
        FloatingActionButton addEventFab = (FloatingActionButton)findViewById(R.id.add_event_fab_submit_event);
        addEventFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> eventData = getEventInfo();
                AddEventToDataBase(eventData);
            }
        });
    }

    private void AddEventToDataBase(ArrayList<String> eventData) {
        MepoEvent event = new MepoEvent(eventData.get(EVENTNAME),eventData.get(EVENTTYPE),eventData.get(EVENTDATE),eventData.get(EVENTADDRESS));
        mDatabaseReference.push().setValue(event);
        Toast.makeText(AddEventActivity.this,"Event added go have fun",Toast.LENGTH_SHORT).show();
        finish();
    }

    private ArrayList<String> getEventInfo(){
        ArrayList<String> eventData = new ArrayList<>();
        eventData.add(((EditText) findViewById(R.id.add_event_event_name)).getText().toString());
        eventData.add(((EditText)findViewById(R.id.add_event_edit_text_address)).getText().toString());
        eventData.add(((Spinner) findViewById(R.id.add_event_type_spinner)).getSelectedItem().toString());
        eventData.add(((EditText) findViewById(R.id.event_date_edit_text)).getText().toString());
        return eventData;
    }

    private void setUpEventImgListener() {
        addEventImg = (ImageView)findViewById(R.id.add_event_img);
        addEventImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, ""), RC_PICK_IMAGE);
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void setUpDateBtnListener() {
        Button dateBtn = (Button) findViewById(R.id.add_event_date_btn);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(1);
            }
        });
    }

    private DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            showDate(year, month + 1, day);
        }
    };

    @Override
    @SuppressWarnings("deprecation")
    protected Dialog onCreateDialog(int id) {
        if (id == 1) {
            return new DatePickerDialog(this, mDateListener, year, month, day);
        }
        return super.onCreateDialog(id);
    }


    private void showDate(int year, int month, int days) {
        //create the date string and update the edit text
        String dateStr = days+"/"+month+"/"+year;
        EditText eventDateEditText = (EditText)findViewById(R.id.event_date_edit_text);
        eventDateEditText.setText(dateStr);
    }


    private void setUpSpinner() {
        options = getResources().getStringArray(R.array.add_event_types);
        Spinner eventTypeSpinner = (Spinner) findViewById(R.id.add_event_type_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, options);
        eventTypeSpinner.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_CANCELED){
            if(requestCode == RC_PICK_IMAGE){
                Uri selectedImgUri = data.getData();
                Bitmap myImg = BitmapFactory.decodeFile(selectedImgUri.getPath());
                addEventImg.setImageBitmap(myImg);
                addEventImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }
    }
}

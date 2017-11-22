package com.example.shaha.mepo;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private static final int RC_PICK_IMAGE = 1;
    private static final int MY_PERMISSION_FINE_LOCATION = 101;
    private static final int PLACE_PICKER_REQUEST = 2;
    String[] options;
    private Calendar calendar;
    private int year, month, day;
    private ImageView addEventImg;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private EditText timeEditText;
    private Calendar fromCal, untilCal; //stores the time of the event
    private boolean fromBtnClicked;
    private Date startTime, endTime;
    private Location selectedPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        requestPermission();

        //get current time
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("Events");

        setupListeners();
    }

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "This app requires location permission to be granted", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    private void setupListeners() {
        setUpDateBtnListener();
        setUpClockBtnListener();
        setUpSpinner();
        setUpEventImgListener();
        setUpAddEventBtnListener();
        setUpPickLocationBtnListener();
    }

    private void setUpPickLocationBtnListener() {
        ImageButton pickPlaceBtn = (ImageButton) findViewById(R.id.add_event_pick_location_btn);
        pickPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open the place picker
                openPlacePicker();
            }
        });
    }

    private void openPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = builder.build(AddEventActivity.this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    private void setUpClockBtnListener() {
        final ImageButton timeFromBtn = (ImageButton) findViewById(R.id.add_event_time_from_btn);
        ImageButton timeUntilBtn = (ImageButton) findViewById(R.id.add_event_time_until_btn);
        timeFromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromBtnClicked = true;
                timeEditText = (EditText) findViewById(R.id.add_event_time_from_edit_text);
                openTimePicker();
            }
        });
        timeUntilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromBtnClicked = false;
                timeEditText = (EditText) findViewById(R.id.add_event_time_until_edit_text);
                openTimePicker();
            }
        });
    }

    private void openTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(AddEventActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true //24 hours
        );
        tpd.show(getFragmentManager(), "TimePickerDialog");
    }

    private void setUpAddEventBtnListener() {
        FloatingActionButton addEventFab = (FloatingActionButton) findViewById(R.id.add_event_fab_submit_event);
        addEventFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Object> eventData = getEventInfo();
                if (eventData == null) {
                    //there was a problem with one of the fields
                    return;
                }
                AddEventToDataBase(eventData);
            }
        });
    }

    private void AddEventToDataBase(ArrayList<Object> eventData) {
        /**
         * change null at end of new Mepo event to current user
         */
        DatabaseReference pushReference = mDatabaseReference.push();
        MepoEvent newMepoEvent = new MepoEvent(eventData,pushReference.getKey());
        pushReference.setValue(newMepoEvent);
        Toast.makeText(AddEventActivity.this, "Event added go have fun", Toast.LENGTH_SHORT).show();
        finish();
    }

    private ArrayList<Object> getEventInfo() {
        ArrayList<Object> eventData = new ArrayList<>();
        //check if all required fields are not empty
        if (isValid()) {
            eventData.add(((EditText) findViewById(R.id.add_event_event_name)).getText().toString());
            eventData.add(selectedPlace);
            eventData.add(((Spinner) findViewById(R.id.add_event_type_spinner)).getSelectedItem().toString());
            eventData.add(startTime);
            eventData.add(endTime);
            eventData.add(MainActivity.getCurrentUser());
            return eventData;
        } else {
            return null;
        }
    }

    private boolean isValid() {
        if (((EditText) findViewById(R.id.add_event_event_name)).getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Event name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (((EditText) findViewById(R.id.add_event_edit_text_address)).getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Event address is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (startTime == null) {
            Toast.makeText(getApplicationContext(), "Start time is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (endTime == null) {
            Toast.makeText(getApplicationContext(), "End time is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setUpEventImgListener() {
        addEventImg = (ImageView) findViewById(R.id.add_event_img);
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
        //initiate the calenders
        fromCal = Calendar.getInstance();
        untilCal = Calendar.getInstance();
        //update the calender
        fromCal.set(Calendar.YEAR, year);
        fromCal.set(Calendar.MONTH, month);
        fromCal.set(Calendar.DAY_OF_MONTH, days);
        untilCal.set(Calendar.YEAR, year);
        untilCal.set(Calendar.MONTH, month);
        untilCal.set(Calendar.DAY_OF_MONTH, days);
        //create the date string and update the edit text
        String dateStr = days + "/" + month + "/" + year;
        EditText eventDateEditText = (EditText) findViewById(R.id.event_date_edit_text);
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
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == RC_PICK_IMAGE) {
                Uri selectedImgUri = data.getData();
                Bitmap myImg = BitmapFactory.decodeFile(selectedImgUri.getPath());
                addEventImg.setImageBitmap(myImg);
                addEventImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else if (requestCode == PLACE_PICKER_REQUEST) {
                Place place = PlacePicker.getPlace(AddEventActivity.this, data);
                MepoCoordinate coordinate = new MepoCoordinate(place.getLatLng().latitude,place.getLatLng().longitude);
                selectedPlace = new Location(place.getName().toString(), place.getAddress().toString(), coordinate);
                updateLocation(place);
            }
        }
    }

    private void updateLocation(Place selectedPlace) {
        EditText placeAddressEditText = (EditText) findViewById(R.id.add_event_edit_text_address);
        placeAddressEditText.setText(selectedPlace.getAddress());
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        //update the edit text to show the time
        String timeStr = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute) + ":" + String.format("%02d", second);
        timeEditText.setText(timeStr);
        Calendar timeCal;

        if (fromBtnClicked) {
            //the user clicked on the form clock dialog
            timeCal = fromCal;
        } else {
            timeCal = untilCal;
        }

        timeCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        timeCal.set(Calendar.MINUTE, minute);
        timeCal.set(Calendar.SECOND, second);

        if (fromBtnClicked) {
            startTime = timeCal.getTime();
        } else {
            endTime = timeCal.getTime();
        }
    }
}

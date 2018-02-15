package com.example.shaha.mepo;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddEventActivity extends AppCompatActivity {
    private static final int RC_PICK_IMAGE = 1;
    private static final int MY_PERMISSION_FINE_LOCATION = 101;
    private static final int PLACE_PICKER_REQUEST = 2;
    String[] options;
    private ImageView addEventImg;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private Location selectedPlace;
    private int day, month, year;
    private int dayFinal, monthFinal, yearFinal;
    DatePickerDialog.OnDateSetListener from_dateListener, to_dateListener;
    private TimePickerDialog.OnTimeSetListener from_timeListener, to_timeListener;
    private Date fromDate, toDate;
    private String eventName;
    private String eventAddress;
    private String startTime;
    private String endTime;
    private double longtitude;
    private double latitude;
    private int type;
    private EditText timeFromEditText, timeUntilEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        requestPermission();

        timeFromEditText = (EditText) findViewById(R.id.add_event_time_from_edit_text);
        timeUntilEditText = (EditText) findViewById(R.id.add_event_time_until_edit_text);

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
        setUpClockBtnListener();
        setUpSpinner();
        setDateAndTimeListener();
        setUpEventImgListener();
        setUpAddEventBtnListener();
        setUpPickLocationBtnListener();
    }

    private void setDateAndTimeListener() {
        from_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                yearFinal = i;
                monthFinal = i1;
                dayFinal = i2;

                fromDate = new GregorianCalendar(yearFinal, monthFinal, dayFinal).getTime();
                //after pick the time
                openTimePicker(from_timeListener);
            }
        };

        from_timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                int hour = i;
                int minute = i1;

                Calendar cal = Calendar.getInstance();
                cal.setTime(fromDate);
                cal.add(Calendar.HOUR_OF_DAY, hour);
                cal.add(Calendar.MINUTE, minute);
                fromDate = cal.getTime();
                SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy HH:mm:00.00");
                Toast.makeText(AddEventActivity.this, ft.format(fromDate).toString(), Toast.LENGTH_SHORT).show();
                timeFromEditText.setText(ft.format(fromDate));
            }
        };

        to_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                yearFinal = i;
                monthFinal = i1;
                dayFinal = i2;

                toDate = new GregorianCalendar(yearFinal, monthFinal, dayFinal).getTime();
                //after pick the time
                openTimePicker(to_timeListener);
            }
        };

        to_timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                int hour = i;
                int minute = i1;

                Calendar cal = Calendar.getInstance();
                cal.setTime(toDate);
                cal.add(Calendar.HOUR_OF_DAY, hour);
                cal.add(Calendar.MINUTE, minute);
                toDate = cal.getTime();
                SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy HH:mm:00.00");
                Toast.makeText(AddEventActivity.this, ft.format(toDate).toString(), Toast.LENGTH_SHORT).show();
                timeUntilEditText.setText(ft.format(toDate));
            }
        };
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
        ImageButton timeFromBtn = (ImageButton) findViewById(R.id.add_event_time_from_btn);
        ImageButton timeUntilBtn = (ImageButton) findViewById(R.id.add_event_time_until_btn);
        timeFromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //first pick the date
                openDatePicker(from_dateListener);
            }
        });
        timeUntilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(to_dateListener);
            }
        });
    }

    private void openDatePicker(DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddEventActivity.this, listener, year, month, day);
        datePickerDialog.show();
    }

    private void openTimePicker(TimePickerDialog.OnTimeSetListener listener) {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddEventActivity.this, listener, hour, minute, true);
        timePickerDialog.show();
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
        if (eventName.equals("")) {
            Toast.makeText(getApplicationContext(), "Event name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (eventAddress.equals("")) {
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
}

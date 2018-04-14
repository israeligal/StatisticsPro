package com.example.rami.statistics_pro.Utils;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rami.statistics_pro.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtils {
    private static DatePickerDialog.OnDateSetListener from_dateListener;
    private static  DatePickerDialog.OnDateSetListener to_dateListener;
    private static int dayFinal, monthFinal, yearFinal;
    private static Date fromDate, toDate;
    private static Date FIRST_RAFFLE_DATE;
    private static Date TODAYS_DATE;

    static {
        FIRST_RAFFLE_DATE = new GregorianCalendar(2002, 3, 13).getTime();
        TODAYS_DATE = Calendar.getInstance().getTime();
    }

    /** Take ChosenNumbersFragment view and sets edit text to handle the dates
     *  Set date picker dialog and set date listeners
     *  Also sets the logic behind it
     */
    public static void setEditTextsDate(EditText timeFromEditText, EditText timeUntilEditText,
                                        final View mview)
    {

        timeFromEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("got clicked");
                openDatePicker(from_dateListener, mview);
            }
        });

        timeUntilEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(to_dateListener, mview);
            }
        });

        setDateListener(timeFromEditText, timeUntilEditText, mview);
    }




    private static void openDatePicker(DatePickerDialog.OnDateSetListener listener, View mview) {
        int day, month, year;

        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mview.getContext(), listener, year, month, day);
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMinDate(FIRST_RAFFLE_DATE.getTime());
        datePicker.setMaxDate(TODAYS_DATE.getTime());
        datePickerDialog.show();
    }

    private static void setDateListener(final EditText timeFromEditText,
                                       final EditText timeUntilEditText, final View mview) {

        from_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                yearFinal = i;
                monthFinal = i1;
                dayFinal = i2;

                fromDate = new GregorianCalendar(yearFinal, monthFinal, dayFinal).getTime();
                SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
                    Toast.makeText(mview.getContext(), ft.format(fromDate).toString(), Toast.LENGTH_SHORT).show();
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
                if (fromDate == null) {
                    Toast.makeText(mview.getContext(), R.string.please_chose_start_date_first, Toast.LENGTH_SHORT).show();

                } else if (fromDate.after(toDate)) {
                    Toast.makeText(mview.getContext(), mview.getContext().getString(R.string.date_isnt_valid), Toast.LENGTH_SHORT).show();

                } else {
                    SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
                    Toast.makeText(mview.getContext(), ft.format(toDate).toString(), Toast.LENGTH_SHORT).show();
                    timeUntilEditText.setText(ft.format(toDate));
                }
            }
        };


    }


}

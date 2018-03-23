package com.example.rami.statistics_pro.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.rami.statistics_pro.Games.GameTripleSeven.GameTripleSeven;
import com.example.rami.statistics_pro.Interfaces.Game;
import com.example.rami.statistics_pro.Interfaces.Raffle;
import com.example.rami.statistics_pro.R;
import com.example.rami.statistics_pro.Utils.CsvUtils;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.ICSVParser;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

public class ChooseNumbersFragment extends Fragment {
    LinearLayout mview;
    Game curGame;
    ArrayList<CheckBox> choosenNumbers;
    TableRow choosenNumbersTableRow;
    private int dayFinal, monthFinal, yearFinal;
    DatePickerDialog.OnDateSetListener from_dateListener, to_dateListener;
    private TimePickerDialog.OnTimeSetListener from_timeListener, to_timeListener;
    private EditText timeFromEditText, timeUntilEditText;
    private Date fromDate, toDate;
    private static String LOG_TAG = ChooseNumbersFragment.class.getName();



    public ChooseNumbersFragment() {
        // Required empty public constructor
        choosenNumbers = new ArrayList<>();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mview = (LinearLayout) inflater.inflate(R.layout.fragment_choose_numbers_statistics, container, false);

        final Context mContext = mview.getContext();
        View.OnClickListener onClickListener = createGameOnClickListerner(mContext);
        // here we can add user choice for different games
        curGame = new GameTripleSeven(mview.getContext(), onClickListener);
        TableLayout gameTable = curGame.getGame_table();
        choosenNumbersTableRow = create_chosen_numbers_table(mContext);
        mview.addView(gameTable, 0);

        setEditTextTime();
        setDateAndTimeListener();
        setSearchButton();

        return mview;
    }

    private void setSearchButton() {
        Button btn = (Button) mview.findViewById(R.id.search_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileReader fileReader = CsvUtils.readCsvFile(mview, curGame.getCsvUrl());
                if (fileReader != null) {
                    loadStatistics(fileReader);
                }
            }
        });
    }

    private void loadStatistics(FileReader fileReader) {
        try {
            CSVReader reader = new CSVReader(fileReader);

            String[] nextLine;
            reader.readNext();// skip headers line
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line

                curGame.addRaffleFromCsv(nextLine);
//                System.out.println(curGame.getGameRaffles().get(0).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //CHECKBOX LOGICS
    private View.OnClickListener createGameOnClickListerner(final Context mContext) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int filled_numbers = curGame.getFilled_numbers();
                CheckBox checkBox = (CheckBox) view;
                if (choosenNumbers.size() >= filled_numbers && checkBox.isChecked()) {
                    Log.d(LOG_TAG, "game clickListener, maximal number has been chosen");

                    checkBox.setChecked(false);
                    Toast.makeText(mContext, "נבחר מספר מקסימלי של מספרים", Toast.LENGTH_SHORT).show();
                } else if (!checkBox.isChecked()) {
                    System.out.println("else if label");
                    int removeNumberIndex = choosenNumbers.indexOf(checkBox);
                    choosenNumbers.remove(checkBox);
                    int i;
                    CharSequence lastText;
                    CharSequence curText = "";
                    for (i = choosenNumbersTableRow.getChildCount() - 1; i >= removeNumberIndex; i--) {
                        TextView textView = (TextView) choosenNumbersTableRow.getChildAt(i);
                        lastText = textView.getText();
                        textView.setText(curText);
                        curText = lastText;
                    }

                } else {
                    System.out.println("else label");
                    TextView textView = (TextView) choosenNumbersTableRow.getChildAt(choosenNumbers.size());
                    choosenNumbers.add(checkBox);
                    textView.setText(checkBox.getText());
                }
            }
        };
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    //TABLE
    private TableRow create_chosen_numbers_table(Context context) {
        TableRow tableRow = (TableRow) mview.findViewById(R.id.chooseNumbersRow);
        for (int i = 0; i < curGame.getFilled_numbers(); i++) {
            TextView curTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.textview_chosen_numbers_triple_seven, null);
            tableRow.addView(curTextView);
        }
        set_chosen_numbers_table_style(context, tableRow);
        return tableRow;
    }

    private void set_chosen_numbers_table_style(Context context, TableRow tableRow) {
        Drawable border = getResources().getDrawable(R.drawable.border);
//        tableRow.setBackgroundColor(Color.RED); set from xml
        float cols_num = tableRow.getChildCount();
        for (int i = 0; i < cols_num; i++) {
            TextView curTextView = (TextView) tableRow.getChildAt(i);

            TableRow.LayoutParams params = (TableRow.LayoutParams) curTextView.getLayoutParams();
            params.weight = 1 / cols_num;

            curTextView.setLayoutParams(params);
            curTextView.setTextSize(32);
            curTextView.setBackground(border);
        }
    }

    //TIME
    private void setEditTextTime() {
        timeFromEditText = (EditText) mview.findViewById(R.id.timeFromEditText);
        timeFromEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("got clicked");
                openDatePicker(from_dateListener);
            }
        });
        timeUntilEditText = (EditText) mview.findViewById(R.id.timeUntilEditText);
        timeUntilEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(to_dateListener);
            }
        });
    }
    private void openDatePicker(DatePickerDialog.OnDateSetListener listener) {
        int day, month, year;

        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mview.getContext(), listener, year, month, day);
        datePickerDialog.show();
    }

    private void setDateAndTimeListener() {

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
                    Toast.makeText(mview.getContext(), getString(R.string.date_isnt_valid), Toast.LENGTH_SHORT).show();

                } else {
                    SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
                    Toast.makeText(getContext(), ft.format(toDate).toString(), Toast.LENGTH_SHORT).show();
                    timeUntilEditText.setText(ft.format(toDate));
                }
            }
        };


    }
}

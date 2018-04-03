package com.example.rami.statistics_pro.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.example.rami.statistics_pro.Games.GameTripleSeven.GameTripleSeven;
import com.example.rami.statistics_pro.Interfaces.Game;
import com.example.rami.statistics_pro.Interfaces.Raffle;
import com.example.rami.statistics_pro.Interfaces.Statistics;
import com.example.rami.statistics_pro.R;
import com.example.rami.statistics_pro.Utils.CsvUtils;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ChooseNumbersFragment extends Fragment {
    LinearLayout mview;
    Game curGame;
    ArrayList<ToggleButton> chosenNumbers;
    TableRow choosenNumbersTableRow;
    private int dayFinal, monthFinal, yearFinal;
    DatePickerDialog.OnDateSetListener from_dateListener, to_dateListener;
    private TimePickerDialog.OnTimeSetListener from_timeListener, to_timeListener;
    private EditText timeFromEditText, timeUntilEditText;
    private Date fromDate, toDate;
    private Button mSearchBtn;
    private static String LOG_TAG = ChooseNumbersFragment.class.getName();



    public ChooseNumbersFragment() {
        // Required empty public constructor
        chosenNumbers = new ArrayList<>();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ScrollView scrollView = (ScrollView) inflater.inflate(R.layout.fragment_choose_numbers_statistics, container, false);
        mview = (LinearLayout) scrollView.getChildAt(0);
        final Context mContext = mview.getContext();
        View.OnClickListener onClickListener = createGameOnClickListerner(mContext);
        // here we can add user choice for different games


        curGame = new GameTripleSeven(mview, onClickListener);
        Statistics curStatistics = curGame.getStatistics();
        TableLayout gameTable = curGame.getGameTable();
        choosenNumbersTableRow = create_chosen_numbers_table(mContext);
        mSearchBtn = mview.findViewById(R.id.search_btn);
        setEditTextTime();
        setDateAndTimeListener();
        setSearchButton();

        return mview;
    }

    private void setSearchButton() {
        Button btn = (Button) mview.findViewById(R.id.search_btn);
        resetView(); // TODO create reset view
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileReader fileReader = CsvUtils.readCsvFile(mview, curGame.getCsvUrl());
                if (fileReader != null) {
                    loadRaffles(fileReader, mview);
                    loadStatistics();
                }
            }
        });
    }

    private void resetView() {
        // TODO create reset view
    }

    private void loadRaffles(FileReader fileReader, View mview) {
        try {
            CSVReader reader = new CSVReader(fileReader);

            String[] nextLine;
            reader.readNext();// skip headers line
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line

                Raffle raffle = curGame.addRaffleFromCsv(nextLine);
                ContentValues raffleContentValues = raffle.raffleToContentValues();
                mview.getContext().getContentResolver().insert(curGame.getSqlRaffleDb(), raffleContentValues);
//                ContentValues raffleNumbersContentValues = raffle.numbersToContentValues(); TODO fix content provider class java
//                mview.getContext().getContentResolver().insert(curGame.getSqlRaffleNumbersDb(), raffleNumbersContentValues);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadStatistics(){
        String timeFrom = timeFromEditText.getText().toString();
        String timeTo = timeUntilEditText.getText().toString();
        TableRow tableRow = mview.findViewById(R.id.statisticsAppearanceRow);
        LinearLayout linearLayout = mview.findViewById(R.id.statisticsLinearLayout);

        Statistics statistics = curGame.getStatistics();
        statistics.time_stats_from_list(timeFrom, timeTo);
        int[] numberAppearance = statistics.statisticsNumberAppearance(chosenNumbers);
        Log.d(LOG_TAG, "numberAppearance " + Arrays.toString(numberAppearance));
        TextView numberone = mview.findViewById(R.id.number_one_tv);
        numberone.setVisibility(TextView.VISIBLE);
        int number = numberAppearance[0];
        numberone.setText(String.valueOf(number));
        tableRow.setVisibility(View.VISIBLE);
        for(int i = 2; i < numberAppearance.length; ++i){// TODO put this inside game related class
            TextView textView = (TextView)tableRow.getChildAt(i);
            int num = numberAppearance[i];
            textView.setText(String.valueOf(num));
            textView.setTextSize(20);
            textView.setVisibility(TextView.VISIBLE);
            textView.setBackgroundColor(Color.BLUE);
            Log.d(LOG_TAG, "set text to" + num);
        }


    }

    //CHECKBOX LOGICS
    private View.OnClickListener createGameOnClickListerner(final Context mContext) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int filled_numbers = curGame.getResult_Number();
                ToggleButton toggleButton = (ToggleButton) view;

                if (chosenNumbers.size() >= filled_numbers && toggleButton.isChecked()) {
                    Log.d(LOG_TAG, "game clickListener, maximal number has been chosen");
                    toggleButton.setChecked(false);
                    Toast.makeText(mContext, "נבחר מספר מקסימלי של מספרים", Toast.LENGTH_SHORT).show();
                } else if (!toggleButton.isChecked()) {
                    Log.d(LOG_TAG, "game clickListener, number " + toggleButton.getText() + " toggle off");
                    int removeNumberIndex = chosenNumbers.indexOf(toggleButton);
                    chosenNumbers.remove(toggleButton);
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
                    Log.d(LOG_TAG, "game clickListener, number " + toggleButton.getText() + " toggle on");
                    TextView textView = (TextView) choosenNumbersTableRow.getChildAt(chosenNumbers.size());
                    chosenNumbers.add(toggleButton);
                    textView.setText(toggleButton.getText());
                }
                if(chosenNumbers.size() < 3){
                    mSearchBtn.setEnabled(false);
                }
                else{
                    mSearchBtn.setEnabled(true);
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
        for (int i = 0; i < curGame.getResult_Number(); i++) {
            TextView curTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.game_chosen_number_text_view, tableRow, false);
            tableRow.addView(curTextView);
        }
        set_chosen_numbers_table_style(context, tableRow);
        return tableRow;
    }

    private void set_chosen_numbers_table_style(Context context, TableRow tableRow) {
//        Drawable border = getResources().getDrawable(R.drawable.border);
        Drawable border = getResources().getDrawable(R.drawable.circular_border);
        tableRow.setBackgroundColor(Color.BLUE); // set from xml
        float cols_num = tableRow.getChildCount();
        for (int i = 0; i < cols_num; i++) {
            TextView curTextView = (TextView) tableRow.getChildAt(i);

            TableRow.LayoutParams params = (TableRow.LayoutParams) curTextView.getLayoutParams();
            params.weight = 1 / cols_num;
            params.setMargins(0,0,5,0);
            curTextView.setLayoutParams(params);
            curTextView.setTextSize(32);
            curTextView.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
//            curTextView.setGravity(View.TEXT_ALIGNMENT_CENTER);
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

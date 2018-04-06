package com.example.rami.statistics_pro.Fragments;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
import com.example.rami.statistics_pro.Utils.GameStringUtils;
import com.example.rami.statistics_pro.Utils.TimeUtils;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ChooseNumbersFragment extends Fragment {
    LinearLayout mview;
    Game curGame;
    ArrayList<ToggleButton> chosenNumbers;
    TableRow choosenNumbersTableRow;
    private Button mSearchBtn;
    EditText timeFromEditText,  timeUntilEditText;
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
    public void onDestroy() {
        super.onDestroy();
        chosenNumbers.clear();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ScrollView scrollView = (ScrollView) inflater.inflate(R.layout.fragment_choose_numbers_statistics, container, false);

        mview = (LinearLayout) scrollView.findViewById(R.id.chooseNumbersLayout);

        View.OnClickListener onClickListener = createGameOnClickListerner();
        // here we can add user choice for different games

        curGame = new GameTripleSeven(mview, onClickListener);
        Statistics curStatistics = curGame.getStatistics();
        choosenNumbersTableRow = create_chosen_numbers_table();
        mSearchBtn = mview.findViewById(R.id.search_btn);

        timeFromEditText =  (EditText) mview.findViewById(R.id.timeFromEditText);
        timeUntilEditText = (EditText) mview.findViewById(R.id.timeUntilEditText);
        TimeUtils.setEditTextsDate(timeFromEditText, timeUntilEditText, mview);
        setSearchButton();





        return scrollView;
    }

    private void setSearchButton() {
        final Button btn = (Button) mview.findViewById(R.id.search_btn);
        resetView(); // TODO create reset view
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn.setEnabled(false);
                FileReader fileReader = CsvUtils.readCsvFile(mview, curGame.getCsvUrl());
                if (fileReader != null) {
                    setStatisticsProgressBar(view);
                    loadRaffles(fileReader, mview);
                    loadStatistics();
                }
                btn.setEnabled(true);
            }
        });
    }

    private void resetView() {
        // TODO create reset view
    }

    private void setStatisticsProgressBar(View view) {
    }

    private void loadRaffles(FileReader fileReader, View mview) {
        ProgressBar progressBar = new ProgressBar(mview.getContext());
        progressBar.setVisibility(View.VISIBLE);
        try {
            CSVReader reader = new CSVReader(fileReader);

            String[] nextLine;
            reader.readNext();// skip headers line
            progressBar.setMax(10000);
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
        finally {
            progressBar.setVisibility(View.GONE);
        }

    }

    private void loadStatistics(){
        String timeFrom = timeFromEditText.getText().toString();
        String timeTo = timeUntilEditText.getText().toString();
        TableRow tableRow = mview.findViewById(R.id.statisticsAppearanceRow);
        LinearLayout linearLayout = mview.findViewById(R.id.statisticsLinearLayout);

        Statistics statistics = curGame.getStatistics();
        statistics.time_stats_from_list(timeFrom, timeTo, mview);
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

    private View.OnClickListener createGameOnClickListerner() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int filled_numbers = curGame.getResult_Number();
                ToggleButton toggleButton = (ToggleButton) view;

                if (chosenNumbers.size() >= filled_numbers && toggleButton.isChecked()) {
                    Log.d(LOG_TAG, "game clickListener, maximal number has been chosen");
                    toggleButton.setChecked(false);
                    Toast.makeText(toggleButton.getContext(), "נבחר מספר מקסימלי של מספרים", Toast.LENGTH_SHORT).show();

                }
                // toggle off
                else if (!toggleButton.isChecked()) {
                    Log.d(LOG_TAG, "game clickListener, number " + toggleButton.getText() + " toggle off");
                    int res_id = GameStringUtils.getNumberResourceName(toggleButton.getText().toString(), toggleButton,false);
                    toggleButton.setBackgroundResource(res_id);
//                    toggleButton.setBackgroundResource(R.drawable.master_a);
                    CharSequence lastText;
                    CharSequence curText = "";
                    int removeNumberIndex = chosenNumbers.indexOf(toggleButton);
                    chosenNumbers.remove(toggleButton);
                    for (int i = choosenNumbersTableRow.getChildCount() - 1; i >= removeNumberIndex; i--) {
                        TextView textView = (TextView) choosenNumbersTableRow.getChildAt(i);
                        lastText = textView.getText();
                        textView.setText(curText);
                        curText = lastText;
                    }

                }
                // toggle on
                else {
                    Log.d(LOG_TAG, "game clickListener, number " + toggleButton.getText() + " toggle on");
                    TextView textView = (TextView) choosenNumbersTableRow.getChildAt(chosenNumbers.size());
                    int res_id = GameStringUtils.getNumberResourceName(toggleButton.getText().toString(), toggleButton,true);
                    toggleButton.setBackgroundResource(res_id); //
//                    toggleButton.setBackgroundResource(R.drawable.master_b);
                    chosenNumbers.add(toggleButton);
                    textView.setText(toggleButton.getText());
                }
                if(chosenNumbers.size() < 3 || TextUtils.isEmpty(timeFromEditText.getText())
                        || TextUtils.isEmpty(timeUntilEditText.getText())){
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
    private TableRow create_chosen_numbers_table() {
        TableRow tableRow = (TableRow) mview.findViewById(R.id.chooseNumbersRow);
        for (int i = 0; i < curGame.getResult_Number(); i++) {
            TextView curTextView = (TextView) LayoutInflater.from(tableRow.getContext()).inflate(R.layout.game_chosen_number_text_view, tableRow, false);
            tableRow.addView(curTextView);
        }
        set_chosen_numbers_table_style(tableRow);
        return tableRow;
    }

    private void set_chosen_numbers_table_style(TableRow tableRow) {
        Drawable border = getResources().getDrawable(R.drawable.circular_border);
        float cols_num = tableRow.getChildCount();
        for (int i = 0; i < cols_num; i++) {
            TextView curTextView = (TextView) tableRow.getChildAt(i);

            TableRow.LayoutParams params = (TableRow.LayoutParams) curTextView.getLayoutParams();
            params.weight = 1 / cols_num;
            params.setMargins(0,0,5,0);
            curTextView.setLayoutParams(params);
            curTextView.setTextSize(32);
            curTextView.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
            curTextView.setBackground(border);
        }
    }

    //TIME

}

package com.example.rami.statistics_pro.Fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.rami.statistics_pro.Games.GameTripleSeven.GameTripleSeven;
import com.example.rami.statistics_pro.Games.GameTripleSeven.RaffleTripleSeven;
import com.example.rami.statistics_pro.Interfaces.Game;
import com.example.rami.statistics_pro.BaseClass.Statistics;
import com.example.rami.statistics_pro.Loaders.OperationSearchLoader;
import com.example.rami.statistics_pro.R;
import com.example.rami.statistics_pro.Utils.CsvUtils;
import com.example.rami.statistics_pro.Utils.GameStringUtils;
import com.example.rami.statistics_pro.Utils.TimeUtils;
import com.example.rami.statistics_pro.data.StatisticsProContracts;

import java.util.ArrayList;


public class ChooseNumbersFragment extends Fragment implements LoaderManager.LoaderCallbacks {
    Game curGame;
    ArrayList<ToggleButton> chosenNumbers;
    TableRow choosenNumbersTableRow;
    private Button mSearchBtn;
    EditText timeFromEditText,  timeUntilEditText;
    private static String LOG_TAG = ChooseNumbersFragment.class.getName();
    public static final int OPERATION_SEARCH_LOADER = 1;
    public static final int OPERATION_STATISTICS_LOADER = 2;
    public static final int CURSOR_LOADER = 3;


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

        LinearLayout mview = (LinearLayout) scrollView.findViewById(R.id.choose_numbers_fragment_layout);

        View.OnClickListener onClickListener = createGameOnClickListener();
        // here we can add user choice for different games

        curGame = new GameTripleSeven(mview, onClickListener);
        choosenNumbersTableRow = create_chosen_numbers_table(mview);
        mSearchBtn = mview.findViewById(R.id.search_btn);

        timeFromEditText =  (EditText) mview.findViewById(R.id.time_from_edit_text);
        timeUntilEditText = (EditText) mview.findViewById(R.id.time_until_edit_text);
        TimeUtils.setEditTextsDate(timeFromEditText, timeUntilEditText, mview);

        setRadioSearchByDatesOrRaffles(mview);
        setSearchButton(mview);

        return scrollView;
    }

    private void setRadioSearchByDatesOrRaffles(final ViewGroup mview) {
        RadioGroup radioGroup = mview.findViewById(R.id.radio_chose_by_date_or_raffle_number);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                LinearLayout dateSearch = (LinearLayout) mview.findViewById(R.id.search_by_date_edit_texts);
                LinearLayout raffleSearch = (LinearLayout) mview.findViewById(R.id.search_by_raffle_number_edit_texts);
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                if(!checkedRadioButton.isChecked()){
                    Log.w(LOG_TAG, "onCheckedChanged btn not checked" + checkedRadioButton.getText());

                    return;
                }
                switch (checkedId){
                    case R.id.radioDate:
                        Log.d(LOG_TAG, "Radio search by dates clicked");
                        dateSearch.setVisibility(View.VISIBLE);
                        raffleSearch.setVisibility(View.GONE);
                        break;
                    case R.id.radioRaffleNumber:
                        Log.d(LOG_TAG, "Radio search by raffle numbers clicked");
                        dateSearch.setVisibility(View.GONE);
                        raffleSearch.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    private void enableSearchButton(){
        if(chosenNumbers.size() < 3){
            mSearchBtn.setEnabled(false);
        }
        else{
            mSearchBtn.setEnabled(true);
        }
    }
    private boolean checkDatesFields(){
        if (TextUtils.isEmpty(timeFromEditText.getText().toString())
        || TextUtils.isEmpty(timeUntilEditText.getText().toString())){
            Toast.makeText(getContext(),getString(R.string.please_chose_dates_first),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void setSearchButton(final ViewGroup mview) {
        Button btn = (Button) mview.findViewById(R.id.search_btn);
        resetView(); // TODO create reset view
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkDatesFields()){
                    Button btn =  view.findViewById(R.id.search_btn);
                    btn.setEnabled(false);
                    String filePath = CsvUtils.readCsvFile(mview, curGame.getCsvUrl());
                    if (filePath != null) {
                        makeOperationLoadRaffles(filePath);

                    }
                }
            }
        });
    }

    private void resetView() {
        // TODO create reset view
    }


    private void makeOperationLoadRaffles(String filePath) {

        Bundle queryBundle = new Bundle();
        queryBundle.putString("filePath", filePath);
        LoaderManager loaderManager = getLoaderManager();
//        Loader<String> loader = loaderManager.getLoader(OPERATION_SEARCH_LOADER);
        Loader<String> loader = loaderManager.getLoader(CURSOR_LOADER);
        if(loader==null){
            loaderManager.initLoader(CURSOR_LOADER, queryBundle, this);
//            loaderManager.initLoader(OPERATION_SEARCH_LOADER, queryBundle, this);

        }else{
            loaderManager.restartLoader(CURSOR_LOADER, queryBundle, this);
//            loaderManager.restartLoader(OPERATION_SEARCH_LOADER, queryBundle, this);
        }
    }


    private View.OnClickListener createGameOnClickListener() {
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

                enableSearchButton();

            }
        };
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    //TABLE
    private TableRow create_chosen_numbers_table(ViewGroup mview) {


        TableRow tableRow = (TableRow) mview.findViewById(R.id.choose_numbers_row);
        for (int i = 0; i < curGame.getResult_Number(); i++) {
            TextView curTextView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.game_chosen_number_text_view, tableRow, false);
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


    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader onCreateLoader(int id, final Bundle args) {
        final ProgressBar mProgressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        switch (id){
            case OPERATION_SEARCH_LOADER:
                return new OperationSearchLoader(getContext());
            case OPERATION_STATISTICS_LOADER:
                return new AsyncTaskLoader<String>(getContext()) {
                    ProgressBar mProgressBar;


                    @Override
                    public String loadInBackground() {


                            ViewGroup mview = getActivity().findViewById(R.id.choose_numbers_fragment_layout);
                            String timeFrom = timeFromEditText.getText().toString();
                            String timeEnd = timeUntilEditText.getText().toString();
                            Statistics statistics = curGame.getStatistics();
                            statistics.time_stats_from_list(timeFrom, timeEnd, mview,chosenNumbers);


                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setVisibility(View.GONE);
                                }




                            });

                        return "Raffles loaded Successfully";

                    }

                    @Override
                    protected void onStartLoading() {
                        mProgressBar = new ProgressBar(getContext(),null,android.R.attr.progressBarStyleHorizontal);
                        mProgressBar.setVisibility(View.VISIBLE);
                        mProgressBar.setMax(10000);

                        Log.d(LOG_TAG,"Start background process");

                        //Think of this as AsyncTask onPreExecute() method,start your progress bar,and at the end call forceLoad();
                        forceLoad();
                    }
                };
            case CURSOR_LOADER:
                return new CursorLoader(getContext()
                        , StatisticsProContracts.TripleSevenRaffleEntry.CONTENT_URI
                        , null, null, null,null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Log.d(LOG_TAG,"result : "+ data);
        switch(loader.getId()){
            case OPERATION_SEARCH_LOADER:
                if(data != null && data.equals("Raffles loaded Successfully")){
                    makeOperationLoadStatistics();
                }
                else{
                    Toast.makeText(getContext(),"בעיה בטעינת הקבצים מאתר הפיס",Toast.LENGTH_SHORT).show();
                }
                break;
            case OPERATION_STATISTICS_LOADER:
                Statistics statistics = curGame.getStatistics();
                ViewGroup mview = (ViewGroup) getActivity().findViewById(R.id.choose_numbers_fragment_layout);
                statistics.addAppearanceRow(mview, chosenNumbers);
                Button btn = getActivity().findViewById(R.id.search_btn);
                btn.setEnabled(true);
                break;
            case CURSOR_LOADER:
                Cursor cursor = (Cursor) data;
                loadRafflesFromDb(cursor);
        }
    }

    private void loadRafflesFromDb(Cursor cursor) {
        if(cursor == null){
            return;
        }

        while(cursor.moveToNext()){
            int raffleId = cursor.getInt(0);
            String raffleDate = cursor.getString(1);
            String raffleNumbersString = cursor.getString(2);
            int[] raffleNumbers = RaffleTripleSeven.getRaffleNumbersIntArray(raffleNumbersString);
            int raffleWinnersNumber = cursor.getInt(3);
            curGame.getGameRaffles().add(new RaffleTripleSeven(raffleDate, raffleId,
                    raffleNumbers, raffleWinnersNumber));
        }

        makeOperationLoadStatistics();

    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

    private void makeOperationLoadStatistics(){
//        ProgressBar progressBar = new ProgressBar(getContext());
//        progressBar.setVisibility(View.VISIBLE);
//        mview.addView(progressBar);

        Bundle queryBundle = new Bundle();
        LoaderManager loaderManager = getLoaderManager();
        Loader<String> loader = loaderManager.getLoader(OPERATION_STATISTICS_LOADER);
        if(loader==null){
            loaderManager.initLoader(OPERATION_STATISTICS_LOADER, queryBundle, this);

        }else{
            loaderManager.restartLoader(OPERATION_STATISTICS_LOADER, queryBundle, this);

        }

    }

}

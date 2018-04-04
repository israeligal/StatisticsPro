package com.example.rami.statistics_pro.Games.GameTripleSeven;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ToggleButton;
import com.example.rami.statistics_pro.Interfaces.Game;
import com.example.rami.statistics_pro.Interfaces.Raffle;
import com.example.rami.statistics_pro.Interfaces.Statistics;
import com.example.rami.statistics_pro.R;
import com.example.rami.statistics_pro.SqlLiteDataBase.StatisticsProContracts;
import com.example.rami.statistics_pro.Utils.GameStringUtils;

import java.util.ArrayList;


public class GameTripleSeven implements Game {

    private static final int TOTAL_NUMBERS = 70;
    static final int FILLED_NUMBERS = 17;
    private static final int RESULT_NUMBER = 7;
    private static final int TABLE_ROWS = 7;
    private static final int TABLE_COLUMNS = 10;
    private static final int IMAGE_HEIGHT = 70;
    private static final int IMAGE_WIDTH = 0;

    private TableLayout gameTable;
    private ArrayList<Raffle> gameRaffles;
    private Uri SQL_RAFFLE_DB = StatisticsProContracts.TripleSevenRaffleEntry.CONTENT_URI;
    private Uri SQL_RAFFLE_NUMBERS_DB = StatisticsProContracts.TripleSevenRaffleNumbersEntry.CONTENT_URI;
    private StatisticsTripleSeven mStatisticsTripleSeven;

    public GameTripleSeven(View view, View.OnClickListener clickListener){
        Context context = view.getContext();

        gameRaffles = new ArrayList<>();
        gameTable = view.findViewById(R.id.gameTableId);
        mStatisticsTripleSeven = new StatisticsTripleSeven(this);

        // create game table
        ToggleButton[][] buttonsArray = new ToggleButton[TABLE_ROWS][TABLE_COLUMNS];
        for(int row = 0; row < buttonsArray.length; row++) {
//            TableRow currentTableRow = (TableRow) LayoutInflater.from(gameTable.getContext()).inflate(R.layout.game_table_row, gameTable, false);
            TableRow currentTableRow = new TableRow(gameTable.getContext());
//            currentTableRow.setWeightSum(1); // a must

//            TableRow currentTableRow = (TableRow)gameTable.getChildAt(row);

            for (int col = 0; col < buttonsArray[row].length; col++) {

                TableRow.LayoutParams btn_params = new TableRow.LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT);
                ToggleButton curButton = createNewToggleButton(currentTableRow,clickListener, row, col);
                buttonsArray[row][col] = curButton;
                btn_params.setMargins(0,0,0,0);
                curButton.setPadding(0,0,0,0);
                currentTableRow.addView(curButton, btn_params);

            }
            TableLayout.LayoutParams row_params = new TableLayout.LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT);
            row_params.setMargins(0,0,0,10);
            currentTableRow.setPadding(0,0,0,0);
//            gameTable.setWeightSum(1);

            gameTable.addView(currentTableRow, row_params);

        }
//        gameTable.setWeightSum(1);
        gameTable.setStretchAllColumns(true);
        gameTable.setPadding(0,5,0,5);

    }
    private ToggleButton createNewToggleButton(TableRow currentTableRow, View.OnClickListener clickListener,
                                               int row, int col){

        int buttonNumber = row * 10 + col + 1;
        int resourceId = GameStringUtils.getNumberResourceName(buttonNumber, currentTableRow,false);
        ToggleButton curButton = new ToggleButton(currentTableRow.getContext());
//        ToggleButton curButton = (ToggleButton) LayoutInflater.from(currentTableRow.getContext()).inflate(R.layout.game_toggle_button_number, currentTableRow, false);
//        ToggleButton curButton = (ToggleButton)currentTableRow.getChildAt(col);

        String buttonNumberString = String.valueOf(buttonNumber);

        curButton.setOnClickListener(clickListener);
        curButton.setText(buttonNumberString); // used for taking the number later
        curButton.setTextOff(buttonNumberString);
        curButton.setTextOn(buttonNumberString);
        curButton.setContentDescription(buttonNumberString);
//        curButton.setTextSize(12);
//        curButton.setBackgroundResource(R.drawable.master_a);
        curButton.setTextSize(0);
        curButton.setBackgroundResource(resourceId);


//        curButton.setScaleX(0.9f);


        return curButton;
    }



    @Override
    public Raffle addRaffleFromCsv(String[] csvString) {
        RaffleTripleSeven raffleTripleSeven = new RaffleTripleSeven(csvString);
        gameRaffles.add(raffleTripleSeven);
        return raffleTripleSeven;
    }

    @Override
    public Uri getSqlRaffleDb() {
        return SQL_RAFFLE_DB;
    }

    @Override
    public Uri getSqlRaffleNumbersDb() {
        return SQL_RAFFLE_NUMBERS_DB;
    }

    public ArrayList<Raffle> getGameRaffles() {
        return gameRaffles;
    }

    public void setGameRaffles(ArrayList<Raffle> gameRaffles) {
        this.gameRaffles = gameRaffles;
    }

    public int getTOTAL_NUMBERS() {
        return TOTAL_NUMBERS;
    }


    @Override
    public int getFilled_numbers() {
        return FILLED_NUMBERS;
    }

    public TableLayout getGameTable() {
        return gameTable;
    }

    public void setGameTable(TableLayout gameTable) {
        this.gameTable = gameTable;
    }


    public String getCsvUrl() {
        return CsvContractTripleSeven.CSV_URL;
    }

    @Override
    public int getResult_Number() {
        return RESULT_NUMBER;
    }

//    public void getStatistics(ArrayList<CheckBox> choosenNumbers, String timeFrom, String timeEnd, TableRow tableRow, ListView listView){
//        StatisticsTripleSeven.time_stats_from_sql(SQL_RAFFLE_DB, choosenNumbers, timeFrom, timeEnd, tableRow, listView);
//        StatisticsTripleSeven.time_stats_from_list(this, choosenNumbers, timeFrom, timeEnd, tableRow, listView);
//    }

    @Override
    public Statistics getStatistics(){
        return mStatisticsTripleSeven;
    }
}
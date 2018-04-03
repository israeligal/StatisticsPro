package com.example.rami.statistics_pro.Games.GameTripleSeven;

import android.content.Context;
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

import java.util.ArrayList;


public class GameTripleSeven implements Game {

    static final int TOTAL_NUMBERS = 70;
    static final int FILLED_NUMBERS = 17;
    static final int RESULT_NUMBER = 7;
    static final int table_rows = 4;
    static final int table_columns = 10;
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
        ToggleButton[][] buttonsArray = new ToggleButton[table_rows][table_columns];
        for(int row = 0; row < buttonsArray.length; row++) {
            TableRow currentTableRow = (TableRow) LayoutInflater.from(gameTable.getContext()).inflate(R.layout.game_table_row, gameTable, false);

//            TableRow currentTableRow = (TableRow)gameTable.getChildAt(row);
            currentTableRow.setWeightSum(1);

            for (int col = 0; col < buttonsArray[row].length; col++) {
                TableRow.LayoutParams btn_params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 4, 0.1f);
                ToggleButton curButton = createNewToggleButton(currentTableRow,clickListener, row, col);
                buttonsArray[row][col] = curButton;
                btn_params.setMargins(5,0,5,0);
                currentTableRow.addView(curButton, btn_params);
            }
            TableLayout.LayoutParams row_params = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 4, 1f);
            gameTable.addView(currentTableRow, row_params);

        }
        gameTable.setStretchAllColumns(true);
        gameTable.setPadding(3,3,3,3);

    }
    private ToggleButton createNewToggleButton(TableRow currentTableRow, View.OnClickListener clickListener,
                                               int row, int col){
        ToggleButton curButton = new ToggleButton(currentTableRow.getContext());
//        ToggleButton curButton = (ToggleButton) LayoutInflater.from(currentTableRow.getContext()).inflate(R.layout.game_toggle_button_number, currentTableRow, false);
//        ToggleButton curButton = (ToggleButton)currentTableRow.getChildAt(col);

        int buttonNumber = row * 10 + col + 1;
        String buttonNumberString = String.valueOf(buttonNumber);

        curButton.setOnClickListener(clickListener);
        curButton.setHeight(4);
        curButton.setText(buttonNumberString);
        curButton.setTextOff(buttonNumberString);
        curButton.setTextOn(buttonNumberString);
        curButton.setTextSize(6);
        curButton.setButtonDrawable(R.drawable.check);

        return curButton;
    }

//    public GameTripleSeven(View view, View.OnClickListener clickListener){
//        Context context = view.getContext();
//
//        gameRaffles = new ArrayList<>();
//        gameTable = view.findViewById(R.id.gameTable);
//        mStatisticsTripleSeven = new StatisticsTripleSeven(this);
//        ToggleButton[][] buttonsArray = new ToggleButton[table_rows][table_columns];
//        for(int row = 0; row < buttonsArray.length; row++) {
//
//            TableRow currentTableRow =new TableRow(context);
//            for (int col = 0; col < buttonsArray[row].length; col++) {
//
//                ToggleButton curButton = createNewToggleButton(view,clickListener, row, col);
//
//                buttonsArray[row][col] = curButton;
//                currentTableRow.addView(curButton);
//            }
//            gameTable.addView(currentTableRow);
//        }
//        gameTable.setStretchAllColumns(true);
//        gameTable.setPadding(5,5,5,5);
//
//    }



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
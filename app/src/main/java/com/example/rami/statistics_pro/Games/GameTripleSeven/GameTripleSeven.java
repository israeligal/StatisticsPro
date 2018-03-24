package com.example.rami.statistics_pro.Games.GameTripleSeven;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.rami.statistics_pro.Interfaces.Game;
import com.example.rami.statistics_pro.Interfaces.Raffle;
import com.example.rami.statistics_pro.Interfaces.Statistics;
import com.example.rami.statistics_pro.SqlLiteDataBase.StatisticsProContracts;

import java.util.ArrayList;


public class GameTripleSeven implements Game {

    static final int TOTAL_NUMBERS = 70;
    static final int FILLED_NUMBERS = 17;
    static final int RESULT_NUMBER = 7;
    static final int table_rows = 7;
    static final int table_columns = 10;
    private TableLayout gameTable;
    private ArrayList<Raffle> gameRaffles;
    private Uri SQL_RAFFLE_DB = StatisticsProContracts.TripleSevenRaffleEntry.CONTENT_URI;
    private Uri SQL_RAFFLE_NUMBERS_DB = StatisticsProContracts.TripleSevenRaffleNumbersEntry.CONTENT_URI;
    private StatisticsTripleSeven mStatisticsTripleSeven;

    public GameTripleSeven(Context context, View.OnClickListener clickListener){
        gameRaffles = new ArrayList<>();
        gameTable = new TableLayout(context);
        mStatisticsTripleSeven = new StatisticsTripleSeven(this);
        CheckBox[][] buttonsArray = new CheckBox[table_rows][table_columns];
        for(int row = 0; row < buttonsArray.length; row++) {
            TableRow currentTableRow = new TableRow(context);
            for (int col = 0; col < buttonsArray[row].length; col++) {

                CheckBox curButton = createNewCheckBox(context,clickListener, row, col);
                buttonsArray[row][col] = curButton;
                currentTableRow.addView(curButton);
            }
            gameTable.addView(currentTableRow);
        }
        gameTable.setPadding(5,5,5,5);

    }



    private CheckBox createNewCheckBox(Context context, View.OnClickListener clickListener,
                                       int row, int col){
        CheckBox curButton = new CheckBox(context);
        int buttonNumber = row * 10 + col + 1;
        String buttonNumberString = String.valueOf(buttonNumber);
        TextView buttonNumberTextView = new TextView(context);
        buttonNumberTextView.setText(buttonNumberString);
        curButton.setOnClickListener(clickListener);
        curButton.setText(buttonNumberString);

        curButton.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

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
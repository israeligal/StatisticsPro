package com.example.rami.statistics_pro.Games.GameTripleSeven;

import android.net.Uri;
import android.view.View;
import android.widget.TableRow;
import android.widget.ToggleButton;

import com.example.rami.statistics_pro.BaseClass.Game;
import com.example.rami.statistics_pro.Interfaces.GameCsvContract;
import com.example.rami.statistics_pro.Interfaces.Raffle;
import com.example.rami.statistics_pro.Utils.GameStringUtils;
import com.example.rami.statistics_pro.data.StatisticsProContracts;

import java.util.ArrayList;


public class GameTripleSeven extends Game {


    private Uri SQL_RAFFLE_DB = StatisticsProContracts.TripleSevenRaffleEntry.CONTENT_URI;


    public GameTripleSeven(View view, View.OnClickListener clickListener){

        gameCsvContract = new CsvContractTripleSeven();
        TOTAL_NUMBERS = 70;
        FILLED_NUMBERS = 17;
        RESULT_NUMBER = 7;
        TABLE_ROWS = 7;
        TABLE_COLUMNS = 10;
        IMAGE_HEIGHT = 70;
        IMAGE_WIDTH = 0;

        gameRaffles = new ArrayList<>();
        mStatistics = new StatisticsTripleSeven(this);
        createGameTable(view, clickListener);
    }

    /** Creates buttons and rows defined by the Game */



    public GameCsvContract getGameCsvContract() {
        return gameCsvContract;
    }


    /** Creates a new toggle button for each number in the game total numbers */

    @Override
    protected ToggleButton createNewToggleButton(TableRow currentTableRow,
                                                 View.OnClickListener clickListener,
                                               int row, int col){

        int buttonNumber = row * 10 + col + 1;
        int resourceId = GameStringUtils.getNumberResourceName(buttonNumber, currentTableRow,
                false);
        ToggleButton curButton = new ToggleButton(currentTableRow.getContext());

        String buttonNumberString = String.valueOf(buttonNumber);

        curButton.setOnClickListener(clickListener);
        curButton.setText(buttonNumberString); // used for taking the number later
        curButton.setTextOff(buttonNumberString);
        curButton.setTextOn(buttonNumberString);
        curButton.setContentDescription(buttonNumberString);
        curButton.setTextSize(0);
        curButton.setBackgroundResource(resourceId);

        return curButton;
    }

    @Override
    public Uri getSqlRaffleDb() {
        return SQL_RAFFLE_DB;
    }

    @Override
    public Raffle addRaffleFromCsv(String[] csvString) {
        RaffleTripleSeven raffleTripleSeven = new RaffleTripleSeven(csvString);
        gameRaffles.add(raffleTripleSeven);
        return raffleTripleSeven;
    }

    @Override
    public String getCsvUrl() {
        return CsvContractTripleSeven.CSV_URL;
    }



//    public void getStatistics(ArrayList<CheckBox> choosenNumbers, String timeFrom, String timeEnd, TableRow tableRow, ListView listView){
//        StatisticsTripleSeven.time_stats_from_sql(SQL_RAFFLE_DB, choosenNumbers, timeFrom, timeEnd, tableRow, listView);
//        StatisticsTripleSeven.time_stats_from_list(this, choosenNumbers, timeFrom, timeEnd, tableRow, listView);
//    }


}
package com.example.rami.statistics_pro.Interfaces;

import android.net.Uri;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ToggleButton;

import com.example.rami.statistics_pro.BaseClass.Statistics;
import com.example.rami.statistics_pro.Games.GameTripleSeven.StatisticsTripleSeven;
import com.example.rami.statistics_pro.R;

import java.util.ArrayList;

public abstract class Game {

    protected ArrayList<Raffle> gameRaffles;
    protected Statistics mStatistics;
    protected TableLayout gameTable;
    protected static int TOTAL_NUMBERS;
    public static int FILLED_NUMBERS;
    protected static int RESULT_NUMBER;
    protected static int TABLE_ROWS;
    protected static int TABLE_COLUMNS;
    protected static int IMAGE_HEIGHT;
    protected static int IMAGE_WIDTH;


    abstract public Uri getSqlRaffleDb();

    abstract public Raffle addRaffleFromCsv(String[] csvString);

    abstract public GameCsvContract getGameCsvContract();

    abstract public String getCsvUrl();

    abstract protected ToggleButton createNewToggleButton(TableRow currentTableRow,
                                                          View.OnClickListener clickListener,
                                                          int row, int col);

    public int getResult_Number() { return RESULT_NUMBER; }

    public Statistics getStatistics(){
        return mStatistics;
    }

    public ArrayList<Raffle> getGameRaffles() {
        return gameRaffles;
    }

    public int getTOTAL_NUMBERS() {
        return TOTAL_NUMBERS;
    }

    protected void createGameTable(View view, View.OnClickListener clickListener){
        gameTable = view.findViewById(R.id.gameTableId);

        // create game table
        ToggleButton[][] buttonsArray = new ToggleButton[TABLE_ROWS][TABLE_COLUMNS];
        for(int row = 0; row < buttonsArray.length; row++) {
            TableRow currentTableRow = new TableRow(gameTable.getContext());

            for (int col = 0; col < buttonsArray[row].length; col++) {

                TableRow.LayoutParams btn_params = new TableRow.LayoutParams(IMAGE_WIDTH,
                        IMAGE_HEIGHT);
                ToggleButton curButton = createNewToggleButton(currentTableRow,clickListener, row,
                        col);
                buttonsArray[row][col] = curButton;
                btn_params.setMargins(0,0,0,0);
                curButton.setPadding(0,0,0,0);
                currentTableRow.addView(curButton, btn_params);

            }
            TableLayout.LayoutParams row_params = new TableLayout.LayoutParams(IMAGE_WIDTH,
                    IMAGE_HEIGHT);
            row_params.setMargins(0,0,0,10);
            currentTableRow.setPadding(0,0,0,0);

            gameTable.addView(currentTableRow, row_params);

        }
        gameTable.setStretchAllColumns(true);
        gameTable.setPadding(0,5,0,5);

    }


}
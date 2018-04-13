package com.example.rami.statistics_pro.Interfaces;

import android.net.Uri;
import android.widget.TableLayout;

import com.example.rami.statistics_pro.BaseClass.Statistics;

import java.util.ArrayList;

public interface Game {


    public int getFilled_numbers();

    public int getTOTAL_NUMBERS();
    public TableLayout getGameTable();

    public void setGameTable(TableLayout gameTable);
    public ArrayList<Raffle> getGameRaffles();

    public void setGameRaffles(ArrayList<Raffle> gameRaffles);

    public Raffle addRaffleFromCsv(String[] csvString);
    public Uri getSqlRaffleDb();
//    public Uri getSqlRaffleNumbersDb();
    public Statistics getStatistics();
    public String getCsvUrl();

    int getResult_Number();

    public GameCsvContract getGameCsvContract();
}
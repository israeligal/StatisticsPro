package com.example.rami.statistics_pro.Interfaces;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;

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
    public Uri getSqlRaffleNumbersDb();
    public Statistics getStatistics();
    public String getCsvUrl();

    int getResult_Number();
}
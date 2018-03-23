package com.example.rami.statistics_pro.Interfaces;

import android.net.Uri;
import android.widget.TableLayout;

import java.util.ArrayList;

public interface Game {

    public int getFilled_numbers();


    public TableLayout getGame_table();

    public void setGame_table(TableLayout game_table);
    public ArrayList<Raffle> getGameRaffles();

    public void setGameRaffles(ArrayList<Raffle> gameRaffles);

    public Raffle addRaffleFromCsv(String[] csvString);
    public Uri getSqlRaffleDb();
    public void loadStatistics();
    public String getCsvUrl();

}
package com.example.rami.statistics_pro.Interfaces;

import android.widget.TableLayout;

import java.util.ArrayList;

public interface Game {

    public int getFilled_numbers();


    public TableLayout getGame_table();

    public void setGame_table(TableLayout game_table);
    public ArrayList<Raffle> getGameRaffles();

    public void setGameRaffles(ArrayList<Raffle> gameRaffles);

    public void addRaffleFromCsv(String[] csvString);

    public String getCsvUrl();

}
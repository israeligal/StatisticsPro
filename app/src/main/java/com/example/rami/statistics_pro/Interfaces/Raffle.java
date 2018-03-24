package com.example.rami.statistics_pro.Interfaces;


import android.content.ContentValues;


public interface Raffle {

    public ContentValues raffleToContentValues();
//    public ContentValues numbersToContentValues();
    public int getmRaffleDay();

    public int getmRaffleMonth();

    public int getmRaffleYear();

    public String getmRaffleDate();

    public int[] getmRaffleResultNumbers();

    public int getmRaffleId();

    public int getmRaffleWinnersNumber();
}

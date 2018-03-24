package com.example.rami.statistics_pro.SqlLiteDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.rami.statistics_pro.SqlLiteDataBase.StatisticsProContracts.TripleSevenRaffleEntry;
import com.example.rami.statistics_pro.SqlLiteDataBase.StatisticsProContracts.TripleSevenRaffleNumbersEntry;
public class StatisticsProDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tripleSevenRaffles.db";


    public StatisticsProDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_T7_RAFFLES_TABLE =
                "CREATE TABLE "+ TripleSevenRaffleEntry.TABLE_NAME  + " ("
                + TripleSevenRaffleEntry.COLUMN_RAFFLE_ID + " TEXT PRIMARY_KEY NOT NULL, "
                + TripleSevenRaffleEntry.COLUMN_RAFFLE_DATE + " TEXT NOT NULL, "
                + TripleSevenRaffleEntry.COLUMN_RAFFLE_DAY + " INTEGER NOT NULL, "
                + TripleSevenRaffleEntry.COLUMN_RAFFLE_MONTH + " INTEGER NOT NULL, "
                + TripleSevenRaffleEntry.COLUMN_RAFFLE_YEAR + " INTEGER NOT NULL, "
                + TripleSevenRaffleEntry.COLUMN_RAFFLE_RESULT_NUMBERS + " STRING NOT NULL, "
                + TripleSevenRaffleEntry.COLUMN_RAFFLE_WINNERS_NUMBER + " INTEGER NOT NULL "
                + ");";

        String CREATE_T7_RAFFLES_NUMBERS_TABLE =
                "CREATE TABLE "+ TripleSevenRaffleNumbersEntry.TABLE_NAME  + " ("
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_ID
                + " TEXT REFERENCES " + TripleSevenRaffleEntry.TABLE_NAME  +
                "(" + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_ID + ") NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_1 + " INTEGER NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_2 + " INTEGER NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_3 + " INTEGER NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_4 + " INTEGER NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_5 + " INTEGER NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_6 + " INTEGER NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_7 + " INTEGER NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_8 + " INTEGER NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_9 + " INTEGER NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_10 + " INTEGER NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_11 + " INTEGER NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_12 + " INTEGER NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_13 + " INTEGER NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_14 + " INTEGER NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_15 + " INTEGER NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_16 + " INTEGER NOT NULL, "
                + TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_17 + " INTEGER NOT NULL "
                + ");";

        String execSql = CREATE_T7_RAFFLES_TABLE + CREATE_T7_RAFFLES_NUMBERS_TABLE;
        db.execSQL("PRAGMA foreign_keys = 1");
        db.execSQL(execSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

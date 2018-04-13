package com.example.rami.statistics_pro.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.rami.statistics_pro.data.StatisticsProContracts.TripleSevenRaffleEntry;
//import com.example.rami.statistics_pro.data.StatisticsProContracts.TripleSevenRaffleNumbersEntry;
public class StatisticsProDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tripleSevenRaffles.db";


    StatisticsProDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_T7_RAFFLES_TABLE =
                "CREATE TABLE "+ TripleSevenRaffleEntry.TABLE_NAME  + " ("
                + TripleSevenRaffleEntry.COLUMN_RAFFLE_ID + " TEXT PRIMARY_KEY NOT NULL, "
                + TripleSevenRaffleEntry.COLUMN_RAFFLE_DATE + " TEXT NOT NULL, "
                + TripleSevenRaffleEntry.COLUMN_RAFFLE_RESULT_NUMBERS + " STRING NOT NULL, "
                + TripleSevenRaffleEntry.COLUMN_RAFFLE_WINNERS_NUMBER + " INTEGER NOT NULL "
                + ");";

        db.execSQL(CREATE_T7_RAFFLES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

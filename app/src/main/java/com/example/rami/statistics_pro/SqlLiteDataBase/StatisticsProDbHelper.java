package com.example.rami.statistics_pro.SqlLiteDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StatisticsProDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tripleSevenRaffles.db";


    public StatisticsProDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENT_TABLE = "CREATE TABLE "+ StatisticsProContracts.TripleSevenRaffleEntry.TABLE_NAME  + " ("
                + StatisticsProContracts.TripleSevenRaffleEntry.COLUMN_RAFFLE_ID + " TEXT PRIMARY_KEY NOT NULL, "
                + StatisticsProContracts.TripleSevenRaffleEntry.COLUMN_RAFFLE_DATE + " TEXT NOT NULL, "
                + StatisticsProContracts.TripleSevenRaffleEntry.COLUMN_RAFFLE_DAY + " INTEGER NOT NULL, "
                + StatisticsProContracts.TripleSevenRaffleEntry.COLUMN_RAFFLE_MONTH + " INTEGER NOT NULL, "
                + StatisticsProContracts.TripleSevenRaffleEntry.COLUMN_RAFFLE_YEAR + " INTEGER NOT NULL, "
                + StatisticsProContracts.TripleSevenRaffleEntry.COLUMN_RAFFLE_RESULT_NUMBERS + " TEXT NOT NULL, "
                + StatisticsProContracts.TripleSevenRaffleEntry.COLUMN_RAFFLE_WINNERS_NUMBER + " INTEGER NOT NULL, "
                + ");";

        db.execSQL(CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

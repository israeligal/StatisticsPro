package com.example.shaha.mepo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.shaha.mepo.data.MepoContracts.EventsEntry;

public class MepoDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mepo.db";


    public MepoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENT_TABLE = "CREATE TABLE "+ EventsEntry.TABLE_NAME  + " ("
                + EventsEntry._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EventsEntry.COLUMN_EVENT_NAME + " TEXT NOT NULL,"
                + EventsEntry.COLUMN_EVENT_HOST_EMAIL + " TEXT NOT NULL, "+
                EventsEntry.COLUMN_EVENT_LOCATION + " TEXT NOT NULL,"+
                EventsEntry.COLUMN_EVENT_TYPE + " TEXT NOT NULL, " +
                EventsEntry.COLUMN_EVENT_START + " TEXT NOT NULL, " +
                EventsEntry.COLUMN_EVENT_END + " TEXT NOT NULL);";

        db.execSQL(CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

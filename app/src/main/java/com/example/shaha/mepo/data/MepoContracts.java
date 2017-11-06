package com.example.shaha.mepo.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public class MepoContracts {
    public static final String CONTENT_AUTHORITY = "com.example.shaha.mepo";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_EVENTS = "events";

public static abstract class EventsEntry implements BaseColumns{
    public static final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVENTS;
    /**
     * The MIME type of the {@link #CONTENT_URI} for a single event.
     */
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVENTS;


    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_EVENTS);
    public static final String TABLE_NAME = "events";

    public static final String _ID = BaseColumns._ID;
    public static final String COLUMN_EVENT_NAME = "eventName";
    public static final String COLUMN_EVENT_TYPE = "type";
    public static final String COLUMN_EVENT_LOCATION = "location";
    public static final String COLUMN_EVENT_START = "startTime";
    public static final String COLUMN_EVENT_END = "endTime";
    public static final String COLUMN_EVENT_HOST_EMAIL = "hostEmail";


    public static final int TYPE_SPORTS = 0;
    public static final int TYPE_EDUCATION = 1;
    public static final int TYPE_YARD_SALE = 2;
    public static final int TYPE_FUN = 3;
    public static final int TYPE_PROMOTION = 4;
    public static final int TYPE_OTHER = 5;




}

}

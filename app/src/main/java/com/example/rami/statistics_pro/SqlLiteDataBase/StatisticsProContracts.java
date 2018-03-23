package com.example.rami.statistics_pro.SqlLiteDataBase;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public class StatisticsProContracts {
    public static final String CONTENT_AUTHORITY = "com.example.rami.statistics_pro";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TRIPLE_SEVEN_RAFFLES = "tripleSevenRaffles";

    public static abstract class TripleSevenRaffleEntry implements BaseColumns {
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRIPLE_SEVEN_RAFFLES;
        /**
         * The MIME type of the {@link #CONTENT_URI} for a single event.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRIPLE_SEVEN_RAFFLES;


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TRIPLE_SEVEN_RAFFLES);
        public static final String TABLE_NAME = "tripleSevenRaffles";

//        public static final String _ID = BaseColumns._ID;
//        public static final String COLUMN_EVENT_FIREBASE_ID = "fireBaseId";
//        public static final String COLUMN_RAFFLE_NAME = "eventName";
        public static final String COLUMN_RAFFLE_ID = "raffleNumber";
        public static final String COLUMN_RAFFLE_RESULT_NUMBERS = "raffleResultNumbers";
        public static final String COLUMN_RAFFLE_WINNERS_NUMBER = "raffleWinnersNumber";
        public static final String COLUMN_RAFFLE_YEAR = "raffleYear";
        public static final String COLUMN_RAFFLE_MONTH = "raffleMonth";
        public static final String COLUMN_RAFFLE_DAY = "raffleDay";
        public static final String COLUMN_RAFFLE_DATE = "raffleDate";

//        public static final int TYPE_SPORTS = 0;
//        public static final int TYPE_EDUCATION = 1;
//        public static final int TYPE_YARD_SALE = 2;
//        public static final int TYPE_FUN = 3;
//        public static final int TYPE_PROMOTION = 4;
//        public static final int TYPE_OTHER = 5;
    }

}

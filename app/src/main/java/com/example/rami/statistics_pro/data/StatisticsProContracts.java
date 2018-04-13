package com.example.rami.statistics_pro.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public class StatisticsProContracts {
    public static final String CONTENT_AUTHORITY = "com.example.rami.statistics_pro";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TRIPLE_SEVEN_RAFFLES = "tripleSevenRaffles";
    public static final String PATH_TRIPLE_SEVEN_RAFFLES_NUMBERS = "tripleSevenRafflesNumbers";

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

        public static final String COLUMN_RAFFLE_ID = "raffleNumber";
        public static final String COLUMN_RAFFLE_WINNERS_NUMBER = "raffleWinnersNumber";
        public static final String COLUMN_RAFFLE_RESULT_NUMBERS = "raffleResultNumbers";
        public static final String COLUMN_RAFFLE_DATE = "raffleDate";


    }
//    public static abstract class TripleSevenRaffleNumbersEntry implements BaseColumns {
//        public static final String CONTENT_LIST_TYPE =
//                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/"
//                        + PATH_TRIPLE_SEVEN_RAFFLES_NUMBERS;
//        /**
//         * The MIME type of the {@link #CONTENT_URI} for a single event.
//         */
//        public static final String CONTENT_ITEM_TYPE =
//                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/"
//                                                                + PATH_TRIPLE_SEVEN_RAFFLES_NUMBERS;
//
//
//        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,
//                                                                 PATH_TRIPLE_SEVEN_RAFFLES_NUMBERS);
//        public static final String TABLE_NAME = "tripleSevenRafflesNumbers";
//
//        public static final String COLUMN_RAFFLE_ID = "raffleId";
//        public static final String COLUMN_RAFFLE_NUMBER_1 = "raffleNumber1";
//        public static final String COLUMN_RAFFLE_NUMBER_2 = "raffleNumber2";
//        public static final String COLUMN_RAFFLE_NUMBER_3 = "raffleNumber3";
//        public static final String COLUMN_RAFFLE_NUMBER_4 = "raffleNumber4";
//        public static final String COLUMN_RAFFLE_NUMBER_5 = "raffleNumber5";
//        public static final String COLUMN_RAFFLE_NUMBER_6 = "raffleNumber6";
//        public static final String COLUMN_RAFFLE_NUMBER_7 = "raffleNumber7";
//        public static final String COLUMN_RAFFLE_NUMBER_8 = "raffleNumber8";
//        public static final String COLUMN_RAFFLE_NUMBER_9 = "raffleNumber9";
//        public static final String COLUMN_RAFFLE_NUMBER_10 = "raffleNumber10";
//        public static final String COLUMN_RAFFLE_NUMBER_11 = "raffleNumber11";
//        public static final String COLUMN_RAFFLE_NUMBER_12 = "raffleNumber12";
//        public static final String COLUMN_RAFFLE_NUMBER_13 = "raffleNumber13";
//        public static final String COLUMN_RAFFLE_NUMBER_14 = "raffleNumber14";
//        public static final String COLUMN_RAFFLE_NUMBER_15 = "raffleNumber15";
//        public static final String COLUMN_RAFFLE_NUMBER_16 = "raffleNumber16";
//        public static final String COLUMN_RAFFLE_NUMBER_17 = "raffleNumber17";
//
//
////        public static final int TYPE_SPORTS = 0;
////        public static final int TYPE_EDUCATION = 1;
////        public static final int TYPE_YARD_SALE = 2;
////        public static final int TYPE_FUN = 3;
////        public static final int TYPE_PROMOTION = 4;
////        public static final int TYPE_OTHER = 5;
//    }

}

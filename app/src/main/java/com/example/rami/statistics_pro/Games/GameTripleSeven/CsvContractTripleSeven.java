package com.example.rami.statistics_pro.Games.GameTripleSeven;


class CsvContractTripleSeven {

    enum DATE_ORDER {
        DAY(0),
        MONTH(1),
        YEAR(2);
        final int value;
        DATE_ORDER (int value) { this.value = value; }
        int getValue () { return value; }
    }

    static final int DATE = 0;
    static final int RAFFLE_NUMBER = 1;
    static final int RESULT_NUMBERS_START = 2;
    static final int RESULT_NUMBERS_END = 18;
    static final int WINNERS_NUMBER = 19;

    static final String CSV_URL = "http://www.pais.co.il/777/Pages/last_Results.aspx?download=1";



}

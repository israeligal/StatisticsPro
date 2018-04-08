package com.example.rami.statistics_pro.Games.GameTripleSeven;

import com.example.rami.statistics_pro.Interfaces.GameCsvContract;

class CsvContractTripleSeven implements GameCsvContract {

    enum DATE_ORDER {
        DAY(0),
        MONTH(1),
        YEAR(2);
        final int value;
        DATE_ORDER (int value) { this.value = value; }
        int getValue () { return value; }
    }

    static final int DATE = 0;
    static final int RAFFLE_ID_NUMBER = 1;
    static final int RESULT_NUMBERS_START = 2;
    static final int WINNERS_NUMBER = 19;

    public  int getDATE() {
        return DATE;
    }

    public  int getRaffleIdNumber() {
        return RAFFLE_ID_NUMBER;
    }

    public  int getResultNumbersStart() {
        return RESULT_NUMBERS_START;
    }

    public  int getWinnersNumber() {
        return WINNERS_NUMBER;
    }

    public  String getCsvUrl() {
        return CSV_URL;
    }

    static final String CSV_URL = "http://www.pais.co.il/777/Pages/last_Results.aspx?download=1";



}

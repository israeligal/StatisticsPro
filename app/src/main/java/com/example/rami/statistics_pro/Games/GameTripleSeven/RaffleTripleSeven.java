package com.example.rami.statistics_pro.Games.GameTripleSeven;


import android.util.Log;

import com.example.rami.statistics_pro.Interfaces.Raffle;

public class RaffleTripleSeven implements Raffle{

    private int mRaffleDay;
    private int mRaffleMonth;
    private int mRaffleYear;
    private int[] mRaffleResultNumbers;
    private int mRaffleNumber;
    private int mRaffleWinnersNumber;
    private static String LOG_TAG = RaffleTripleSeven.class.getName();

    public RaffleTripleSeven(int mRaffleDay, int mRaffleMonth, int mRaffleYear,int mRaffleNumber,
                             int[] mRaffleNumbers, int mRaffleWinnersNumber) {
        this.mRaffleDay = mRaffleDay;
        this.mRaffleMonth = mRaffleMonth;
        this.mRaffleYear = mRaffleYear;
        this.mRaffleNumber = mRaffleNumber;
        this.mRaffleResultNumbers = mRaffleNumbers;
        this.mRaffleWinnersNumber = mRaffleWinnersNumber;

    }
    public RaffleTripleSeven(String[] csvString) {
        int[] raffleDate = extractRaffleDateFromCSv(csvString);
        int[] raffleNumbers = extractRaffleResultNumbersFromCsv(csvString);
        int raffleWinnersNumber = extractRaffleWinnersNumberFromCSv(csvString);
        this.mRaffleDay = raffleDate[0];
        this.mRaffleMonth = raffleDate[1];
        this.mRaffleYear = raffleDate[2];
        this.mRaffleResultNumbers = raffleNumbers;
        this.mRaffleWinnersNumber =raffleWinnersNumber;
    }




    private static RaffleTripleSeven makeRaffleFromCsv(String[] csvString){
        int[] raffleDate = extractRaffleDateFromCSv(csvString);
        int[] raffleResultNumbers = extractRaffleResultNumbersFromCsv(csvString);
        int raffleNumber = extractRaffleNumberFromCSv(csvString);
        int raffleWinnersNumber = extractRaffleWinnersNumberFromCSv(csvString);
        return new RaffleTripleSeven(
                raffleDate[0],
                raffleDate[1],
                raffleDate[2],
                raffleNumber,
                raffleResultNumbers,
                raffleWinnersNumber
                );
    }

    private static int extractRaffleWinnersNumberFromCSv(String[] csvString) {
        String raffleStringNumber = csvString[CsvContractTripleSeven.WINNERS_NUMBER];
        return Integer.parseInt(raffleStringNumber);
    }
    private static int extractRaffleNumberFromCSv(String[] csvString) {
        String raffleStringNumber = csvString[CsvContractTripleSeven.RAFFLE_NUMBER];
        return Integer.parseInt(raffleStringNumber);
    }

    private static int[] extractRaffleDateFromCSv(String[] csvString){
        int[] dateArray = new int[3];
        String date = csvString[CsvContractTripleSeven.DATE];
        String day = date.substring(0, 2);
        String month = date.substring(3, 5);
        String year = date.substring(6, 8);
        dateArray[CsvContractTripleSeven.DATE_ORDER.DAY.getValue()] = Integer.parseInt(day);
        dateArray[CsvContractTripleSeven.DATE_ORDER.MONTH.getValue()] = Integer.parseInt(month);
        dateArray[CsvContractTripleSeven.DATE_ORDER.YEAR.getValue()] = Integer.parseInt(year);
        return dateArray;
    }

    private static int[] extractRaffleResultNumbersFromCsv(String[] csvString) {
        int[] raffleNumbers = new int[GameTripleSeven.FILLED_NUMBERS];
        int csvResultNumbersStart = CsvContractTripleSeven.RESULT_NUMBERS_START;
        for (int i = 0; i < GameTripleSeven.FILLED_NUMBERS; i++){
            raffleNumbers[i] = Integer.parseInt(csvString[csvResultNumbersStart + i]);
        }
        return raffleNumbers;
    }

    @Override
    public String toString() {
        StringBuilder numbersString = new StringBuilder();
        for (int num : mRaffleResultNumbers){
            numbersString.append(num).append(" ");
        }
        return  String.valueOf(mRaffleDay) + "\n" +
                String.valueOf(mRaffleMonth) + "\n" +
                String.valueOf(mRaffleYear) + "\n" +
                numbersString.toString() + "\n";

    }
}

package com.example.rami.statistics_pro.Games.GameTripleSeven;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.rami.statistics_pro.Interfaces.Raffle;
import com.example.rami.statistics_pro.data.StatisticsProContracts.TripleSevenRaffleEntry;
//import com.example.rami.statistics_pro.data.StatisticsProContracts.TripleSevenRaffleNumbersEntry;

import java.util.Arrays;

public class RaffleTripleSeven implements Raffle, Parcelable{

    private String mRaffleDate;
    private int[] mRaffleResultNumbers;
    private int mRaffleId;
    private int mRaffleWinnersNumber;
    private static String LOG_TAG = RaffleTripleSeven.class.getName();

    public RaffleTripleSeven(String mRaffleDate, int mRaffleId,
                             int[] mRaffleNumbers, int mRaffleWinnersNumber) {

        this.mRaffleDate = mRaffleDate;
        this.mRaffleId = mRaffleId;
        this.mRaffleResultNumbers = mRaffleNumbers;
        this.mRaffleWinnersNumber = mRaffleWinnersNumber;

    }
    public RaffleTripleSeven(String[] csvString) {
        this.mRaffleId = extractRaffleNumberFromCSv(csvString);
        int[] raffleNumbers = extractRaffleResultNumbersFromCsv(csvString);
        int raffleWinnersNumber = extractRaffleWinnersNumberFromCSv(csvString);
        this.mRaffleDate = csvString[CsvContractTripleSeven.DATE];
        this.mRaffleResultNumbers = raffleNumbers;
        this.mRaffleWinnersNumber =raffleWinnersNumber;
    }

    protected RaffleTripleSeven(Parcel in) {
        mRaffleDate = in.readString();

//        String raffleResultNumbersString = in.readString(); //read numbers string array to int array
//        String[] s = raffleResultNumbersString.split(",");
//        for (int curr = 0; curr < s.length; curr++)
//            mRaffleResultNumbers[curr] = Integer.parseInt(s[curr]);
//        mRaffleResultNumbers = in.createIntArray();
        in.readIntArray(mRaffleResultNumbers);
        mRaffleId = in.readInt();
        mRaffleWinnersNumber = in.readInt();
    }

    public static final Creator<RaffleTripleSeven> CREATOR = new Creator<RaffleTripleSeven>() {
        @Override
        public RaffleTripleSeven createFromParcel(Parcel in) {
            return new RaffleTripleSeven(in);
        }

        @Override
        public RaffleTripleSeven[] newArray(int size) {
            return new RaffleTripleSeven[size];
        }
    };


    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;
        RaffleTripleSeven raffleTripleSeven = (RaffleTripleSeven)obj;
        return mRaffleId == raffleTripleSeven.mRaffleId;
    }

    private static int extractRaffleWinnersNumberFromCSv(String[] csvString) {
        String raffleStringNumber = csvString[CsvContractTripleSeven.WINNERS_NUMBER];
        return Integer.parseInt(raffleStringNumber);
    }
    private static int extractRaffleNumberFromCSv(String[] csvString) {
        String raffleStringNumber = csvString[CsvContractTripleSeven.RAFFLE_ID_NUMBER];
        return Integer.parseInt(raffleStringNumber);
    }

//    private static int[] extractRaffleDateFromCSv(String[] csvString){
//        int[] dateArray = new int[3];
//        String date = csvString[CsvContractTripleSeven.DATE];
//        String day = date.substring(0, 2);
//        String month = date.substring(3, 5);
//        String year = date.substring(6);
//        dateArray[CsvContractTripleSeven.DATE_ORDER.DAY.getValue()] = Integer.parseInt(day);
//        dateArray[CsvContractTripleSeven.DATE_ORDER.MONTH.getValue()] = Integer.parseInt(month);
//        dateArray[CsvContractTripleSeven.DATE_ORDER.YEAR.getValue()] = Integer.parseInt(year);
//        return dateArray;
//    }

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
        return  String.valueOf(mRaffleId) +  "\n" +
                numbersString.toString() + "\n";

    }

    public String getmRaffleDate() {
        return mRaffleDate;
    }

    public int[] getmRaffleResultNumbers() {
        return mRaffleResultNumbers;
    }

    public int getmRaffleId() {
        return mRaffleId;
    }

    public int getmRaffleWinnersNumber() {
        return mRaffleWinnersNumber;
    }
    public String getRaffleNumbersString(){

        return Arrays.toString(mRaffleResultNumbers);
    }
    public static int[] getRaffleNumbersIntArray(String raffleNumberString){
        raffleNumberString = raffleNumberString.substring(1,raffleNumberString.length()-1);
        raffleNumberString = raffleNumberString.replaceAll("\\s","");
        String[] stringArray = raffleNumberString.split(",");
        int[] raffleNumberIntArray = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            raffleNumberIntArray[i] = Integer.parseInt(stringArray[i]);
            }
        return raffleNumberIntArray;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mRaffleDate);
        dest.writeIntArray(mRaffleResultNumbers);
        dest.writeInt(mRaffleId);
        dest.writeInt(mRaffleWinnersNumber);
    }

    public ContentValues raffleToContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TripleSevenRaffleEntry.COLUMN_RAFFLE_ID, mRaffleId);
        contentValues.put(TripleSevenRaffleEntry.COLUMN_RAFFLE_DATE,  mRaffleDate);
        contentValues.put(TripleSevenRaffleEntry.COLUMN_RAFFLE_RESULT_NUMBERS,  getRaffleNumbersString());
        contentValues.put(TripleSevenRaffleEntry.COLUMN_RAFFLE_WINNERS_NUMBER,  mRaffleWinnersNumber);
        return contentValues;
    }
//    public ContentValues numbersToContentValues(){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_ID, mRaffleId);
//        StringBuilder stringBuilder = new StringBuilder(TripleSevenRaffleNumbersEntry.COLUMN_RAFFLE_NUMBER_1);
//        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
//        for (int i = 0 ; i < mRaffleResultNumbers.length ; i++) {
//            contentValues.put(stringBuilder.toString() + i,  mRaffleResultNumbers[i]);
//        }
//
//        return contentValues;
//    }
}

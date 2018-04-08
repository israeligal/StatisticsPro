package com.example.rami.statistics_pro;

import com.example.rami.statistics_pro.Games.GameTripleSeven.RaffleTripleSeven;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void createRaffleTripleSeven_isCorrect() throws Exception {
        String[] csvString = {"04/04/2018", "9161", "1","2", "3", "1","2", "33", "4","2", "36", "1","2", "3", "70","1", "55", "1","66", "5"};


        RaffleTripleSeven raffleTripleSeven = new RaffleTripleSeven(csvString);
        System.out.println(raffleTripleSeven.toString());

    }

}


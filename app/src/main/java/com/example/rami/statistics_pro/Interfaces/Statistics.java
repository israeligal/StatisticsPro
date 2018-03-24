package com.example.rami.statistics_pro.Interfaces;


import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TableRow;

import java.util.ArrayList;

public interface Statistics {

    int[] statisticsNumberAppearance(ArrayList<CheckBox> chosenNumbers);

    public void time_stats_from_list(String timeFromFull, String timeEndFull);

}

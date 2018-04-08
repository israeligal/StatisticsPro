package com.example.rami.statistics_pro.Interfaces;


import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;

public interface Statistics {

    int[] statisticsNumberAppearance(ArrayList<ToggleButton> chosenNumbers);

    public void time_stats_from_list(String timeFromFull, String timeEndFull, ViewGroup view, ArrayList<ToggleButton> chosenNumbers);

    void addAdditionalStatistics(LinearLayout mview);
}

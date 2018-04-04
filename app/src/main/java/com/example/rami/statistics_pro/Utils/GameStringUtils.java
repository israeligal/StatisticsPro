package com.example.rami.statistics_pro.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;


public class GameStringUtils {
    private static StringBuilder res_name = new StringBuilder("num_");
    private static int start_len = res_name.length();


    public static int getNumberResourceName(int num, View view, boolean checked){
        Resources resources = view.getResources();
        Context context = view.getContext();
        res_name.setLength(start_len);
        if (num < 10){
            res_name.append("0");
        }
        if(!checked) {
            res_name.append(num).append("_a");
        }
        else {
            res_name.append(num).append("_b");
        }


        // resource id
        return resources.getIdentifier(res_name.toString(), "drawable",
                context.getPackageName());
    }
    public static int getNumberResourceName(String num, View view, boolean checked){
        Resources resources = view.getResources();
        Context context = view.getContext();
        res_name.setLength(start_len);
        if (Integer.parseInt(num) < 10){
            res_name.append("0");
        }
        if(!checked) {
            res_name.append(num).append("_a");
        }
        else {
            res_name.append(num).append("_b");
        }


        // resource id
        return resources.getIdentifier(res_name.toString(), "drawable",
                context.getPackageName());
    }



}

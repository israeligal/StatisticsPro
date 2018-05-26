package com.example.rami.statistics_pro.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.EditText;

import com.example.rami.statistics_pro.R;


public class GameUtils {
    private static StringBuilder res_name = new StringBuilder("num_");
    private static int start_len = res_name.length();


    public static int getNumberResourceId(int num, View mview, boolean checked){
        Resources resources = mview.getResources();
        Context context = mview.getContext();
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
    public static int getNumberResourceId(String num, View mview, boolean checked){
        return getNumberResourceId(Integer.parseInt(num), mview, checked);
    }

    public static void setRaffleNumbersEditTextsListeners(View mview){ //TODO FILL UP FUNC!!
        final EditText rafflesFromEditText =  (EditText) mview.findViewById(R.id.raffles_from_edit_text);
        EditText rafflesUntilEditText = (EditText) mview.findViewById(R.id.raffles_until_edit_text);

        rafflesFromEditText.setOnClickListener(new View.OnClickListener() { //TODO get last raffle number and bet 0 to IT
            @Override
            public void onClick(View v) {

            }
        });
        rafflesFromEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}

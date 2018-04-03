package com.example.rami.statistics_pro.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.rami.statistics_pro.R;

public class CustomToggleButton extends ToggleButton {

    public CustomToggleButton(Context context) {
        super(context);
        init();
    }

    public CustomToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomToggleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.game_toggle_button_number, (ViewGroup)getRootView());

    }
}


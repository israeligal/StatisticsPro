package com.example.shaha.mepo;

import android.content.Context;
import android.widget.ListView;

import com.example.shaha.mepo.Adapters.EventsListAdapter;
import com.example.shaha.mepo.Utils.MepoEventUtils;

import java.util.ArrayList;


public class MepoEventListView {

    private ListView mListView;
    private EventsListAdapter mListAdapter;
    private Context mContext;
    private ArrayList<MepoEvent> mEvents;

    static {

    }

    public MepoEventListView(ListView listView, Context context) {
        mListView = listView;
        mEvents = new ArrayList<>(); // TODO REMOVE THIS LINE AFTER FIXING DATABASE
        mListAdapter = new EventsListAdapter(context, mEvents);
        mContext = context;
        mListView.setAdapter(mListAdapter);
        MepoEventUtils.addChildListener(mListAdapter);
    }

}


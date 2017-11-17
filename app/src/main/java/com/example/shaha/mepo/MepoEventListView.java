package com.example.shaha.mepo;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.shaha.mepo.Adapters.EventsListAdapter;
import com.example.shaha.mepo.Utils.MepoEventUtils;

import java.util.ArrayList;


public class MepoEventListView {

    private ListView mListView;
    private EventsListAdapter mListAdapter;
    private Context mContext;
    private ArrayList<MepoEvent> mEvents;

    public MepoEventListView(ListView listView, Context context) {
        mListView = listView;
        mEvents = MepoEventUtils.getEvents();
        mListAdapter = new EventsListAdapter(context,mEvents);
        mContext = context;
        mListView.setAdapter(mListAdapter);
        MepoEventUtils.addChildListener(mListAdapter,null);
        setOnItemClickListener();
    }

    private void setOnItemClickListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext,EventInfoPopUpActivity.class);
                MepoEvent selectedEvent = mListAdapter.getItem(i);
                intent.putExtra("event", selectedEvent);
                mContext.startActivity(intent);
            }
        });
    }
}


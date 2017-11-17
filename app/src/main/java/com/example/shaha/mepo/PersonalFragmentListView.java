package com.example.shaha.mepo;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.shaha.mepo.Adapters.MyMepoEventsCursorAdapter;
import com.example.shaha.mepo.Utils.MepoEventUtils;
import com.example.shaha.mepo.Utils.SqlLiteUtils;
import com.example.shaha.mepo.data.MepoContracts;


public class PersonalFragmentListView {

    private ListView mListView;
    private MyMepoEventsCursorAdapter mListAdapter;
    private Context mContext;

    public PersonalFragmentListView(ListView listView, Context context) {
        mListView = listView;
        mListAdapter = new MyMepoEventsCursorAdapter(context, null);
        mContext = context;
        mListView.setAdapter(mListAdapter);
//        SqlLiteUtils.addListener(mListAdapter);
//        MepoEventUtils.addChildListener(mListAdapter, mContext);
        setOnItemClickListener();
    }

    public MyMepoEventsCursorAdapter getmListAdapter() {
        return mListAdapter;
    }

    private void setOnItemClickListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, EventInfoPopUpActivity.class);
                Uri currentEventUri = ContentUris.withAppendedId(MepoContracts.EventsEntry.CONTENT_URI, l);
                intent.setData(currentEventUri);

                mContext.startActivity(intent);
            }
        });
    }
}




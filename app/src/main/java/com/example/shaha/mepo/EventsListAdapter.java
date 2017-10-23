package com.example.shaha.mepo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shaha on 18/10/2017.
 */

public class EventsListAdapter extends ArrayAdapter<MepoEvent>{
    public EventsListAdapter(@NonNull Context context, List<MepoEvent> events) {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View eventListItemView = convertView;

        if(eventListItemView==null){
            eventListItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_event,parent,false);
        }

        MepoEvent currentEvent = getItem(position);

        TextView eventTitleTextView = eventListItemView.findViewById(R.id.event_title_text_view);
        eventTitleTextView.setText(currentEvent.getEventName());

        return eventListItemView;
    }
}

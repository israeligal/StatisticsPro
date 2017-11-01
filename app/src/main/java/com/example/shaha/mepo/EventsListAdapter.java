package com.example.shaha.mepo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        TextView eventTitleTextView = (TextView)eventListItemView.findViewById(R.id.event_title_text_view);
        eventTitleTextView.setText(currentEvent.getEventName());

        TextView eventDateTextView = (TextView)eventListItemView.findViewById(R.id.list_item_event_date);
        eventDateTextView.setText(getDate(currentEvent.getStartTime()));

        TextView eventTimeTextView = (TextView)eventListItemView.findViewById(R.id.list_item_event_time);
        eventTimeTextView.setText(getStartTime(currentEvent.getStartTime())+"-"+getEndTime(currentEvent.getEndTime()));

        return eventListItemView;
    }

    private String getStartTime(Date date) {
        return new SimpleDateFormat("HH:mm").format(date);
    }

    private String getEndTime(Date date) {
        return new SimpleDateFormat("HH:mm").format(date);
    }

    /**
     * Get the event start date and represent it as a string
     * @param startTime
     * @return
     */
    private String getDate(Date startTime) {
        return DateFormat.getDateInstance(DateFormat.SHORT).format(startTime);
    }
}

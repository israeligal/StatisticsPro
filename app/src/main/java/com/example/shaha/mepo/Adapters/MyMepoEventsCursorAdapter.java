package com.example.shaha.mepo.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shaha.mepo.R;
import com.example.shaha.mepo.data.MepoContracts;


public class MyMepoEventsCursorAdapter extends CursorAdapter {

    public MyMepoEventsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_event, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.event_title_text_view);

        // Find the columns of pet attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(MepoContracts.EventsEntry.COLUMN_EVENT_NAME);

        // Read the pet attributes from the Cursor for the current pet
        String eventName = cursor.getString(nameColumnIndex);

        // If the pet breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.
        if (TextUtils.isEmpty(eventName)) {
            eventName = context.getString(R.string.test_event);
        }

        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(eventName);

    }


}

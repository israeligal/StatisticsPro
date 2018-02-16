package com.example.shaha.mepo;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by shaha on 16/02/2018.
 */

public class EventItem implements ClusterItem {
    private final LatLng mPosition;
    private String mTitle;
    private String mSnippet;
    private MepoEvent event;

    public EventItem(double lat, double lng, MepoEvent event) {
        mPosition = new LatLng(lat, lng);
        this.event = event;
    }

    public EventItem(double lat, double lng, String title, String snippet, MepoEvent event) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
        this.event = event;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }

    public MepoEvent getEvent() {
        return event;
    }
}

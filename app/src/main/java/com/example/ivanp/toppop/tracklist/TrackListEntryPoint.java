package com.example.ivanp.toppop.tracklist;

import java.util.List;

public class TrackListEntryPoint {
    private List<TrackList> data;

    public TrackListEntryPoint(List<TrackList> data) {
        this.data = data;
    }

    public List<TrackList> getData() {
        return data;
    }

    public void setData(List<TrackList> data) {
        this.data = data;
    }
}

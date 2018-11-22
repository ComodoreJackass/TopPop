package com.example.ivanp.toppop.deezer;

import com.example.ivanp.toppop.TopSongs;


import java.util.List;

public class Tracks {
    private List<TopSongs> data = null;

    public Tracks(List<TopSongs> data) {
        this.data = data;
    }

    public List<TopSongs> getData() {
        return data;
    }

    public void setData(List<TopSongs> data) {
        this.data = data;
    }
}

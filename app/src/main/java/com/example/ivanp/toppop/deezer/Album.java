package com.example.ivanp.toppop.deezer;

public class Album {

    private String title;
    private String tracklist;
    private String cover_big;

    public Album(String title, String tracklist, String cover) {
        this.title = title;
        this.tracklist = tracklist;
        this.cover_big=cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTracklist() {
        return tracklist;
    }

    public String getCover() {
        return cover_big;
    }

}

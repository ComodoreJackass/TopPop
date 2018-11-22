package com.example.ivanp.toppop;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.example.ivanp.toppop.deezer.Artist;
import com.example.ivanp.toppop.deezer.Album;

public class TopSongs {
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("artist")
    @Expose
    private Artist artist;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    private Album album;


    public TopSongs(Integer position, String title, Artist artist, Integer duration, Album album) {
        this.position = position;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.album = album;
    }

    public Integer getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public Integer getDuration() {
        return duration;
    }

    public Album getAlbum() {
        return album;
    }

}

package com.example.ivanp.toppop.deezer;

public class Response {
    private Tracks tracks;

    public Response(Tracks tracks) {
        this.tracks = tracks;
    }

    public Tracks getTracks() {
        return tracks;
    }
}

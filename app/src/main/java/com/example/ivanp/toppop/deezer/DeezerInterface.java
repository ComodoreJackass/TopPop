package com.example.ivanp.toppop.deezer;

import com.example.ivanp.toppop.tracklist.TrackListEntryPoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DeezerInterface {

    String BASE_URL = "https://api.deezer.com/";

    //call for top songs
    @GET("editorial/0/charts")
    Call<Response> getTracks();

    //call for track list
    @GET("{url}")
    Call<TrackListEntryPoint> getSongNames(@Path(value="url", encoded = true) String url);

}

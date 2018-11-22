package com.example.ivanp.toppop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivanp.toppop.deezer.DeezerInterface;
import com.example.ivanp.toppop.tracklist.TrackList;
import com.example.ivanp.toppop.tracklist.TrackListEntryPoint;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {
    ArrayList<TrackList> trackList;
    Toolbar myToolbar;
    String trackNames = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        myToolbar = findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        trackList = new ArrayList<>();
        contentFactory();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private void contentFactory() {
        TextView songDetails = findViewById(R.id.details_song);
        String songString;
        String albumCallString;


        if (getIntent().hasExtra("song_title") && getIntent().hasExtra("song_author")) {
            songString = getIntent().getStringExtra("song_title");
            songString += " - " + getIntent().getStringExtra("song_author");
            songDetails.setText(songString);
        }

        if (getIntent().hasExtra("album_title")) {
            trackNames += getIntent().getStringExtra("album_title") + ":\n\n";
        }

        if (getIntent().hasExtra("track_call")) {
            albumCallString = getIntent().getStringExtra("track_call");
            // removing baseURL
            albumCallString = albumCallString.substring(23);
            fetchMeSomeTracks(albumCallString);
        }

        if (getIntent().hasExtra("album_cover")) {
            String albumCover = getIntent().getStringExtra("album_cover");

            //loading cover image
            ImageView coverPic = findViewById(R.id.album_cover);
            Picasso.get().load(albumCover).into(coverPic);
        }
    }

    private void fetchMeSomeTracks(String albumCallString) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DeezerInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DeezerInterface deezerInterface = retrofit.create(DeezerInterface.class);
        Call<TrackListEntryPoint> call = deezerInterface.getSongNames(albumCallString);

        call.enqueue(new Callback<TrackListEntryPoint>() {
            @Override
            public void onResponse(Call<TrackListEntryPoint> call, Response<TrackListEntryPoint> response) {
                if (response.isSuccessful()) {
                    TextView albumData = findViewById(R.id.details_album);
                    TrackListEntryPoint res;

                    res = response.body();
                    int size = res.getData().size();

                    for (int i = 0; i < size; i++) {
                        trackNames += "\t\t\t" + res.getData().get(i).getTitle() + "\n";
                    }

                    albumData.setText(trackNames);
                } else {
                    Toast.makeText(getApplicationContext(), "404", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TrackListEntryPoint> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

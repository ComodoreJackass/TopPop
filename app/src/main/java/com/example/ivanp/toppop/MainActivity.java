package com.example.ivanp.toppop;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.ivanp.toppop.deezer.DeezerInterface;
import com.example.ivanp.toppop.deezer.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<TopSongs> topSongs;
    RecyclerView rvTopSongs;
    SwipeRefreshLayout srLayout;
    TopSongsAdapter adapter;
    Toolbar myToolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.normal_sort: {
                Collections.sort(topSongs, new Comparator<TopSongs>() {
                    @Override
                    public int compare(TopSongs o1, TopSongs o2) {
                        return o1.getPosition() - o2.getPosition();
                    }
                });
            }
            break;
            case R.id.asc_sort: {
                Collections.sort(topSongs, new Comparator<TopSongs>() {
                    @Override
                    public int compare(TopSongs o1, TopSongs o2) {
                        return o1.getDuration() - o2.getDuration();
                    }
                });
            }
            break;
            case R.id.desc_sort: {
                Collections.sort(topSongs, new Comparator<TopSongs>() {
                    @Override
                    public int compare(TopSongs o1, TopSongs o2) {
                        return o2.getDuration() - o1.getDuration();
                    }
                });
            }
            break;
        }
        adapter = new TopSongsAdapter(topSongs);
        //Attach the adapter to the recyclerview to populate items
        rvTopSongs.setAdapter(adapter);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar stuff
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        rvTopSongs = findViewById(R.id.rvTopSongs);
        //Set Layout manager to position the items
        rvTopSongs.setLayoutManager(new LinearLayoutManager(this));

        topSongs = new ArrayList<TopSongs>();

        adapter = new TopSongsAdapter(topSongs);
        rvTopSongs.setAdapter(adapter);

        getTracks();

        srLayout = findViewById(R.id.swipe_layout);
        srLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTracks();
            }
        });
    }

    private void getTracks() {

        topSongs.clear();
        //Clearing the current RecyclerView to indicate loading
        adapter = new TopSongsAdapter(topSongs);
        rvTopSongs.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DeezerInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DeezerInterface deezerInterface = retrofit.create(DeezerInterface.class);

        Call<Response> call = deezerInterface.getTracks();

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                //poor naming choices
                Response res = response.body();
                int size = res.getTracks().getData().size();
                for (int j = 0; j < size; j++) {
                    topSongs.add(res.getTracks().getData().get(j));
                }

                adapter = new TopSongsAdapter(topSongs);
                rvTopSongs.setAdapter(adapter);
                srLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                srLayout.setRefreshing(false);
            }
        });
    }
}

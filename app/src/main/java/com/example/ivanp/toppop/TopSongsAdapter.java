package com.example.ivanp.toppop;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class TopSongsAdapter extends RecyclerView.Adapter<TopSongsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView songNameTextView;
        TextView songRankTextView;
        TextView songLengthTextView;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            //Stores the itemView in a public final member variable that can be used
            //to access the context from any ViewHolder instance
            super(itemView);

            songNameTextView = itemView.findViewById(R.id.song_name);
            songRankTextView = itemView.findViewById(R.id.song_ranking);
            songLengthTextView = itemView.findViewById(R.id.song_length);

            linearLayout = itemView.findViewById(R.id.song_container);
        }
    }

    Context context;
    private List<TopSongs> mTopSongs;

    public TopSongsAdapter(List<TopSongs> topSongs) {
        this.mTopSongs = topSongs;
    }


    //Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public TopSongsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //Inflate the custom layout
        View topSongView = inflater.inflate(R.layout.song_layout, parent, false);

        //return a new holder instance
        ViewHolder viewHolder = new ViewHolder(topSongView);
        return viewHolder;
    }

    //Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull TopSongsAdapter.ViewHolder viewHolder, int position) {
        //Get the data model based on position
        TopSongs topSong = mTopSongs.get(position);

        String title = topSong.getTitle() + "\n" + topSong.getArtist().getName();
        //formating time
        Integer minutes = topSong.getDuration() / 60;
        Integer seconds = topSong.getDuration() % 60;
        String length = "";
        length += (minutes > 10) ? minutes.toString() : "0" + minutes.toString();
        length += ":";
        length += (seconds > 10) ? seconds.toString() : "0" + seconds.toString();

        String rank = topSong.getPosition().toString();

        //Set item views based on your views and data model
        viewHolder.songNameTextView.setText(title);
        viewHolder.songRankTextView.setText(rank);
        viewHolder.songLengthTextView.setText(length);

        //onClick listener
        viewHolder.linearLayout.setOnClickListener((view) -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("song_title", topSong.getTitle());
            intent.putExtra("song_author", topSong.getArtist().getName());
            intent.putExtra("album_title", topSong.getAlbum().getTitle());
            intent.putExtra("track_call", topSong.getAlbum().getTracklist());
            intent.putExtra("album_cover", topSong.getAlbum().getCover());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mTopSongs.size();
    }
}

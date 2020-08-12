package com.example.myanimelist.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myanimelist.DetailAnimeActivity;
import com.example.myanimelist.R;
import com.example.myanimelist.model.Anime;

import java.util.List;


public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {
    // mendeklarasi List dan Context
    private List<Anime> animeList;
    private Context context;

    // membuat Constructor
    public AnimeAdapter(List<Anime> animeList, Context context) {
        this.animeList = animeList;
        this.context = context;
    }

    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anime_list, parent, false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AnimeViewHolder holder, int position) {
        final Anime anime = animeList.get(position);
        final String id = anime.getId();
        // minyampan data kedalam xml
        holder.txtTitle.setText(anime.getTitle());
        holder.txtEpisode.setText("Episode :" + anime.getEpisode());
        Glide.with(context).load(anime.getImageUrl()).centerCrop().into(holder.imgCover);
        holder.imgCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailAnimeActivity.class);
                intent.putExtra("id", anime.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public class AnimeViewHolder extends RecyclerView.ViewHolder {
        // deklarasi Texview dan ImageView
        private TextView txtTitle, txtEpisode;
        private ImageView imgCover;

        public AnimeViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtEpisode = itemView.findViewById(R.id.txtEpisode);
            imgCover = itemView.findViewById(R.id.imgCover);
        }
    }
}

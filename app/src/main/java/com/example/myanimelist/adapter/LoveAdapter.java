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

public class LoveAdapter extends RecyclerView.Adapter<LoveAdapter.LoveViewHolder> {
    // deklarasi List dan context;
    private List<Anime> animeList;
    private Context context;

    // membuat constructor
    public LoveAdapter(List<Anime> animeList, Context context) {
        this.animeList = animeList;
        this.context = context;
    }

    @NonNull
    @Override
    public LoveAdapter.LoveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.love_list, parent, false);
        return new LoveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoveAdapter.LoveViewHolder holder, final int position) {
        // menyimpan data dari kedalam xml
        final String id = animeList.get(position).getId();
        holder.txtTitle.setText(animeList.get(position).getTitle());
        Glide.with(context).load(animeList.get(position).getImageUrl()).centerCrop().into(holder.imgCover);
        holder.txtDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailAnimeActivity.class);
                intent.putExtra("id", animeList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

        public class LoveViewHolder extends RecyclerView.ViewHolder {
        // mendeklarasi TextView dan ImageView
        private TextView txtTitle, txtDetail;
        private ImageView imgCover;

        public LoveViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            imgCover = itemView.findViewById(R.id.imgCover);
            txtDetail = itemView.findViewById(R.id.txtDetail);
        }
    }
}

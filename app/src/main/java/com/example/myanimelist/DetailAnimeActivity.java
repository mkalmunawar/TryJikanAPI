package com.example.myanimelist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myanimelist.JSON.Link;
import com.example.myanimelist.database.LoveTable;
import com.example.myanimelist.model.Anime;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

public class DetailAnimeActivity extends AppCompatActivity {
    // Deklarasi TextView, ImageView, Floating Action Button, Table Loves Sql Lite dan String;
    TextView tvTitle, tvScores, tvEpisodes, tvFollowing, tvSynopsis;
    ImageView ivCover;
    FloatingActionButton fabLove;
    LoveTable loveTable;
    String title, imgCover;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_anime);
        // menghilangkan actionbar pada menu Detail Anime
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_indigo_blue));
            actionBar.hide();
        }
        // merubah ketebalan shadow action bar
        getSupportActionBar().setElevation(0);
        // membuat status bar transparant
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        tvTitle = findViewById(R.id.tvTitle);
        tvScores = findViewById(R.id.tvScores);
        tvEpisodes = findViewById(R.id.tvEpisodes);
        tvFollowing = findViewById(R.id.tvFollowing);
        tvSynopsis = findViewById(R.id.tvSynopsis);
        ivCover = findViewById(R.id.ivCover);
        fabLove = findViewById(R.id.fabLove);
        loveTable = new LoveTable(this);

        // mengambil data dari home fragment
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        // definisi url API detail anime
        String url = Link.DETAIL_ANIME;
        // parsing API
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    title = object.getString("title");
                    imgCover = object.getString("image_url");
                    tvTitle.setText(object.getString("title"));
                    tvScores.setText(object.getString("score"));
                    tvEpisodes.setText(object.getString("episodes"));
                    tvFollowing.setText(object.getString("favorites"));
                    tvSynopsis.setText(object.getString("synopsis"));
                    // memasukan gambar menggunakan library Glide
                    Glide.with(DetailAnimeActivity.this).load(object.getString("image_url")).centerCrop().into(ivCover);
                } catch (Exception e) {
                    System.out.println("error :" + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        // event listener untuk menimpan data kedalam table love sqlite
        fabLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailAnimeActivity.this, title, Toast.LENGTH_SHORT).show();
                Anime anime = new Anime(id, title, imgCover);
                loveTable.create(anime);
                setResult(201);
            }
        });
    }
}
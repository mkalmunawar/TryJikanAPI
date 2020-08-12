package com.example.myanimelist.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myanimelist.adapter.AnimeAdapter;
import com.example.myanimelist.JSON.Link;
import com.example.myanimelist.R;
import com.example.myanimelist.model.Anime;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    // mendeklarasi adapter, arraylist, recyclerview
    private AnimeAdapter animeAdapter;
    private ArrayList<Anime> animeList = new ArrayList();
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);

        // memanggil link api
        String url = Link.GET_SEASON_2020;
        // memparsing api
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray animes = object.getJSONArray("anime");
                    for (int i = 0; i < animes.length(); i++) {
                        JSONObject animeObject = animes.getJSONObject(i);
                        Anime anime = new Anime(
                                animeObject.getString("title"),
                                animeObject.getString("episodes"),
                                animeObject.getString("image_url"),
                                animeObject.getString("mal_id")
                        );
                        animeList.add(anime);
                    }
                    // menyimpan data api kedalam arrayList lalu menyimpan nya ke adapter
                    animeAdapter = new AnimeAdapter(animeList, getContext());
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    // menyimpan adapter kedalam recyclerView
                    recyclerView.setAdapter(animeAdapter);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
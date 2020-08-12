package com.example.myanimelist.ui.love;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myanimelist.R;
import com.example.myanimelist.adapter.LoveAdapter;
import com.example.myanimelist.database.LoveTable;
import com.example.myanimelist.model.Anime;

import java.util.ArrayList;

public class LoveFragment extends Fragment {
    // mendeklarasi adapater, recylerview, dan table love sqlite
    private LoveAdapter loveAdapter;
    private RecyclerView recyclerView;
    private LoveTable loveTable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_loves, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rvLove);
        // memanggil method getData
        getData();
    }

    private void getData() {
        // instansiasi table love
        loveTable = new LoveTable(getContext());
        // memasukan data pada table love kedalam arrayList
        ArrayList<Anime> loves = loveTable.selectAll();
        System.out.println(loves.size());
        // memanggil adapter
        loveAdapter = new LoveAdapter(loves,getContext());
        // membuat recycler dalam bentuk linear layout dengan layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        // menyimpan adapter love pada reclyerview
        recyclerView.setAdapter(loveAdapter);
    }
}
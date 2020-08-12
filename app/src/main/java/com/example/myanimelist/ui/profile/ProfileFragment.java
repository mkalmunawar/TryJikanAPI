package com.example.myanimelist.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myanimelist.JSON.Link;
import com.example.myanimelist.R;
import com.example.myanimelist.model.SharedPreference;

import org.json.JSONObject;

public class ProfileFragment extends Fragment {
    // mendeklarasi Textview dan Session
    TextView tvFullname, tvEmail, tvGroup, tvClass, tvNrp;
    SharedPreference sp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvFullname = view.findViewById(R.id.tvFullname);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvClass = view.findViewById(R.id.tvClass);
        tvGroup = view.findViewById(R.id.tvGroup);
        tvNrp = view.findViewById(R.id.tvNrp);
        // memanggil sebuah session
        sp = new SharedPreference(getContext());
        // url api untuk mendapatkan user berdasarkan id
        String url = Link.GET_USER_BY_ID+sp.getKeyid();
        // memparsing API user berdasarkan id
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("data");
                    tvFullname.setText(data.getString("fullname"));
                    tvNrp.setText(data.getString("nrp"));
                    tvEmail.setText(data.getString("email"));
                    tvClass.setText(data.getString("classes"));
                    tvGroup.setText(data.getString("group"));
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
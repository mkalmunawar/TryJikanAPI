package com.example.myanimelist.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    private static final String keyid = "id";
    private SharedPreferences sp;
    private Context context;
    private SharedPreferences.Editor editor;

    public SharedPreference(Context context) {
        this.context = context;
    }

    public String getKeyid() {
        sp = context.getSharedPreferences(keyid, Context.MODE_PRIVATE);
        return sp.getString(keyid, "null");
    }

    public void setKeyId(String id){
        sp = context.getSharedPreferences(keyid, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString(keyid, id);
        editor.commit();
    }
}

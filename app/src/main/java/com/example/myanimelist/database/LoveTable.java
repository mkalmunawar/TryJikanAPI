package com.example.myanimelist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myanimelist.model.Anime;

import java.util.ArrayList;

public class LoveTable extends SQLiteOpenHelper {
    Context context;

    // constructor
    public LoveTable(Context context) {
        super(context, Constant.DATABSE_NAME, null, Constant.DATABASE_VERSION);
        this.context = context;
    }

    // membuat table love
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + Constant.DATABSE_TABLE
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constant.KEY_ID + " TEXT (50) NOT NULL,"
                + Constant.KEY_TITLE + " TEXT (50),"
                + Constant.KEY_URL + " TEXT (300))";
        sqLiteDatabase.execSQL(sqlCreateTable);
    }

    // memperbaharui apabila  sudah ada table love
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlDrop = "DROP TABLE IF EXISTS " + Constant.DATABSE_TABLE;
        String sqlCreateTable = "CREATE TABLE " + Constant.DATABSE_TABLE
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constant.KEY_ID + " TEXT (50) NOT NULL,"
                + Constant.KEY_TITLE + " TEXT (50),"
                + Constant.KEY_URL + " TEXT (300))";
        sqLiteDatabase.execSQL(sqlDrop);
        sqLiteDatabase.execSQL(sqlCreateTable);
        onCreate(sqLiteDatabase);
    }

    // menyimpan data kedalam table love
    public void create(Anime anime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.KEY_ID, anime.getId());
        values.put(Constant.KEY_TITLE, anime.getTitle());
        values.put(Constant.KEY_URL, anime.getImageUrl());
        db.insert(Constant.DATABSE_TABLE, null, values);
        db.close();
    }

    // mengambil semua data pada sqlite dan memasukan kedalam arrayList
    public ArrayList<Anime> selectAll() {
        ArrayList<Anime> animeArrayList = new ArrayList<>();
        String selectAllQuery = "SELECT * FROM " + Constant.DATABSE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllQuery, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(1);
                String title = cursor.getString(2);
                String imgUrl = cursor.getString(3);
                Anime anime = new Anime(id, title, imgUrl);
                animeArrayList.add(anime);
                cursor.moveToNext();
            }
        }
        return animeArrayList;
    }
}

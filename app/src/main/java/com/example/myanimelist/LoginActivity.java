package com.example.myanimelist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myanimelist.JSON.Link;
import com.example.myanimelist.model.SharedPreference;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {
    // mendeklarasi EditText, TextView, Button, dan Session
    EditText etNrp, etPassword;
    TextView tvRegister;
    Button btnLogin;
    SharedPreference sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etNrp = findViewById(R.id.etNrp);
        etPassword = findViewById(R.id.etPassword);
        tvRegister = findViewById(R.id.tvRegister);
        btnLogin = findViewById(R.id.btnLogin);

        // menghilangkan actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_indigo_blue));
            actionBar.hide();
        }
        // menghilangkan shadow actionbar
        getSupportActionBar().setElevation(0);
        // membuat status bar transparant
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // event listener untuk pindah kehalaman intent
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    public void login() {
        // memasukan value pada xml kedalam variable string
        final String nrp = etNrp.getText().toString();
        final String password = etPassword.getText().toString();
        // instansiasi jSONObject
        final JSONObject jsonObject = new JSONObject();
        final String responseServer;
        // link api
        final String url = Link.LOGIN;
        try {
            // menyimpan data kedalam bentuk json untuk di post
            jsonObject.put("nrp", nrp);
            jsonObject.put("password", password);
        } catch (Exception e) {
            // catch the error
        }
        // mempost json
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    setResult(1);
                    JSONObject object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("data");
                    String id = data.getString("id");
                    // apabila berhasil maka akan masuk kedalam halaman home
                    if (object.getString("succeed").equals("true")) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        sp = new SharedPreference(LoginActivity.this);
                        sp.setKeyId(id);
                        startActivity(intent);
                    } else{
                        Toast.makeText(LoginActivity.this, "Password atau Email Salah", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    System.out.println("error : " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("hello", "Error :" + error.getMessage());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return jsonObject.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }
}
package com.example.myanimelist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class RegisterActivity extends AppCompatActivity {
    EditText etNrp, etName, etMail, etPassword, etClass, etGroup;
    TextView tvLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // menghilangkan action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_indigo_blue));
            actionBar.hide();
        }
        // menghilangkan shadow pada actionbar
        getSupportActionBar().setElevation(0);
        // menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        etNrp = findViewById(R.id.etNrp);
        etName = findViewById(R.id.etName);
        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        etClass = findViewById(R.id.etClass);
        etGroup = findViewById(R.id.etGroup);
        tvLogin = findViewById(R.id.tvLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // eventListener untuk pindah intent dari register ke activity login
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // memanggil methode insertUser
                insertUser();
            }
        });
    }

    public void insertUser() {
        final String nrp = etNrp.getText().toString();
        final String name = etName.getText().toString();
        final String mail = etMail.getText().toString();
        final String group = etGroup.getText().toString();
        final String classes = etClass.getText().toString();
        final String password = etPassword.getText().toString();
        // link api
        final String url = Link.INSERT_USER;
        final JSONObject jsonObject = new JSONObject();
        // menyimpan data kedalam body json
        try {
            jsonObject.put("nrp", nrp);
            jsonObject.put("password", password);
            jsonObject.put("fullname", name);
            jsonObject.put("email", mail);
            jsonObject.put("group", group);
            jsonObject.put("classes", classes);
        } catch (Exception e) {
            // catch the error
        }
        // method post json
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    setResult(1);
                    Toast.makeText(RegisterActivity.this, "Nama: "+name+" Berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
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
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(stringRequest);
    }
}
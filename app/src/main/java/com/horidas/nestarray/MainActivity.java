package com.horidas.nestarray;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    TextView tvDisplay;
    ProgressBar progressBar;
    Button loadBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);
        progressBar = findViewById(R.id.progressBar);
        loadBtn = findViewById(R.id.loadBtn);

        String url = "https://hey-php1.000webhostapp.com/apps/Nest_Array.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                           progressBar.setVisibility(View.GONE);

                        Log.d("onResponse: ",jsonObject.toString());

                        try {
                            String name = jsonObject.getString("name");
                            String email = jsonObject.getString("email");

                            tvDisplay.setText("");
                            tvDisplay.append("Name:"+name+"\nEmail: "+email+"\n");

                            JSONArray jsonArray = jsonObject.getJSONArray("book");
                            for (int x=0;x<jsonArray.length();x++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                String bookName = jsonObject1.getString("name");
                                String bookPrice = jsonObject1.getString("price");
                                tvDisplay.append("BookName: "+bookName+"  ");
                                tvDisplay.append("Price: "+bookPrice+"\n\n");
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                  progressBar.setVisibility(View.GONE);
                  loadBtn.setText("Net Error!");
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

       loadBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               progressBar.setVisibility(View.VISIBLE);
               loadBtn.setText("Loading...!");
                requestQueue.add(jsonObjectRequest);
           }
       });
    }
}
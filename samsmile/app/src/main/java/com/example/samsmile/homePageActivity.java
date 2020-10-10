package com.example.samsmile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class homePageActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    private ProgressBar pb;
    private EditText searchEt;
    private ImageButton filterBtn;
    private RecyclerView sourceRv;

    private ArrayList<ModelSourceList> sourceLists;
    private AdapterSourceList adapterSourceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        firebaseAuth = FirebaseAuth.getInstance();
        pb = findViewById(R.id.progressBar);
        searchEt = findViewById(R.id.searchEt);
        filterBtn = findViewById(R.id.filterBtn);
        sourceRv = findViewById(R.id.recyclerView);

        loadSources();
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    adapterSourceList.getFilter().filter(s);
                }
                catch (Exception e){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nav, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void checkUserSatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            finish();
        }
        else{
            startActivity(new Intent(homePageActivity.this, MainActivity.class));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout){
            firebaseAuth.signOut();
            checkUserSatus();
        }
        return super.onOptionsItemSelected(item);
    }



    private void loadSources() {
        try{

            sourceLists = new ArrayList<>();
            sourceLists.clear();
            pb.setVisibility(View.VISIBLE);

            String url = "https://newsapi.org/v2/sources?apiKey="+Constants.API_KEY;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("sources");

                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String id = jsonObject1.getString("id");
                            String name = jsonObject1.getString("name");
                            String description = jsonObject1.getString("description");
                            String url = jsonObject1.getString("url");
                            String country = jsonObject1.getString("country");
                            String category = jsonObject1.getString("category");
                            String language = jsonObject1.getString("language");

                            ModelSourceList model = new ModelSourceList(
                                    ""+id,
                                    ""+name,
                                    ""+description,
                                    ""+url,
                                    ""+category,
                                    ""+language,
                                    ""+country);

                            sourceLists.add(model);

                        }
                        pb.setVisibility(View.GONE);
                        adapterSourceList = new AdapterSourceList(homePageActivity.this, sourceLists);
                        sourceRv.setLayoutManager(new LinearLayoutManager(homePageActivity.this));
                        sourceRv.setAdapter(adapterSourceList);
                    }
                    catch (Exception e){
                        pb.setVisibility(View.GONE);
                        Toast.makeText(homePageActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(homePageActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
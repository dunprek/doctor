package don.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import don.myapplication.R;
import don.myapplication.adapter.AdapterView;
import don.myapplication.helper.Config;
import don.myapplication.model.Doctor;

public class ListDoctorActivity extends AppCompatActivity {
    private final String TAG = "ListDoctorActivity";
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdapterView adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdoctor);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(ListDoctorActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        requestJsonObject();

    }


    private void requestJsonObject() {
        RequestQueue queue = Volley.newRequestQueue(this);


        final StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.URL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response);
                GsonBuilder builder = new GsonBuilder();

                Gson mGson = builder.create();

                List<Doctor> posts = Arrays.asList(mGson.fromJson(response, Doctor[].class));
                adapter = new AdapterView(getApplicationContext(), posts);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }


}
package don.myapplication.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import don.myapplication.R;
import don.myapplication.controller.AppController;
import don.myapplication.helper.Config;

/**
 * Created by Aditya on 5/25/2016.
 */
public class ThirdActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String url;
    TextView tvid, tvname, tvspecialty, tvarea, tvrate, tvrecommendation, tvschedule, tvexperience, tvdescription;
    ImageView ivImage;

    private static String TAG = ThirdActivity.class.getSimpleName();

    // Progress dialog
    private ProgressDialog pDialog;
    private String jsonResponse;
    String id, name, speciality, area, idr, rec, schedule, exp, des, photo;
    Double rate;


    long lng, lat;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);
        tvid = (TextView) findViewById(R.id.tvId);
        tvname = (TextView) findViewById(R.id.tv_name);
        tvspecialty = (TextView) findViewById(R.id.tv_specialty);
        tvarea = (TextView) findViewById(R.id.tv_area);
        tvrate = (TextView) findViewById(R.id.tv_rate);
        tvrecommendation = (TextView) findViewById(R.id.tvRecommendation);
        tvschedule = (TextView) findViewById(R.id.tvSchedule);
        tvexperience = (TextView) findViewById(R.id.tv_exp);
        tvdescription = (TextView) findViewById(R.id.tv_desc);
        ivImage = (ImageView) findViewById(R.id.ivProfile);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        RequestQueue queue = Volley.newRequestQueue(this);

        id = getIntent().getExtras().getString("id");
        url = Config.URL_TEST + id + ".json";

        makeJsonObjectRequest();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void makeJsonObjectRequest() {

        showpDialog();
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    id = response.getString("id");
                    name = response.getString("name");
                    speciality = response.getString("speciality");
                    area = response.getString("area");
                    idr = response.getString("currency");
                    rate = response.getDouble("rate");
                    rec = response.getString("recommendation");
                    schedule = response.getString("schedule");
                    exp = response.getString("experience");
                    des = response.getString("description");
                    photo = response.getString("photo");

                    lng = response.getLong("longitute");
                    lat = response.getLong("latitude");

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 17));
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .snippet(speciality)
                            .title("Dr " + name));

                    String pattern = "###,###,###";
                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
                    String format = decimalFormat.format(rate);

                    tvname.setText(name);
                    tvspecialty.setText(speciality);
                    tvarea.setText(area);
                    tvrate.setText(idr + " " + format);
                    tvrecommendation.setText(rec);
                    tvschedule.setText(schedule);
                    tvexperience.setText(exp + " Years of Experience ");
                    tvdescription.setText(des);

                    Picasso.with(getApplication()).load(photo).into(ivImage);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}










package don.myapplication.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import don.myapplication.R;
import don.myapplication.helper.Config;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView text;
    private ArrayList<String> mStringList = new ArrayList<>();

    //cek koneksi internet apakah ada atau tidak
    private boolean haveNetWorkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netinfo = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo ni : netinfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;

            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;

        }
        return haveConnectedWifi || haveConnectedMobile;


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (haveNetWorkConnection())

        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Internet Connection Available");
            alertDialogBuilder.setMessage("Yes, you are online, internet connection available");
//            Toast.makeText(MainActivity.this, "Internet Connection Available", Toast.LENGTH_LONG).show();
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
//                    Toast.makeText(MainActivity.this,"Internet Connection Available", Toast.LENGTH_LONG).show();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else

        {
            // Display message in dialog box if you have not internet connection
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Offline");
            alertDialogBuilder.setMessage("Please Check Your Internet Connection");
//            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
//                    Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }


        text = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);


        //eksekusi jsonparse
        new JSONParse().execute();


    }


    public void getDoctor(View v) {
        startActivity(new Intent(this, ListDoctorActivity.class));
    }

    private class JSONParse extends AsyncTask<String, Void, String[]> {

        private String[] getDataFromJson(String JsonStr) throws JSONException {
            JSONArray jsonarray = new JSONArray(JsonStr);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String areaa = jsonobject.getString("area");
                String cityy = jsonobject.getString("city");
                mStringList.add(areaa + " - " + cityy);
                Log.d("TAG", mStringList.toString());
            }
            return null;
        }

        @Override
        protected String[] doInBackground(String... args) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String JsonStr = null;
            try {
//                String link = "http://52.76.85.10/test/location.json";
                URL url = new URL(Config.URL_AREA);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                JsonStr = buffer.toString();
            } catch (IOException e) {
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }
            }

            try {
                return getDataFromJson(JsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String[] json) {
            String[] mStringArray = new String[mStringList.size()];
            mStringArray = mStringList.toArray(mStringArray);
            ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mStringArray);
            text.setAdapter(adapter);
            if (text != null) {
                text.setThreshold(1);
            }
        }
    }

}


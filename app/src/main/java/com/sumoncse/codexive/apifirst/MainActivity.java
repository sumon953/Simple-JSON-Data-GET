package com.sumoncse.codexive.apifirst;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView flowerTV;
    private final String FLOWERS_URL = "http://services.hanselandpetal.com/feeds/flowers.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flowerTV = findViewById(R.id.flowername);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info.isAvailable() && info.isConnected()){

            //JsonDownload Object Create;
            new JsonDownload().execute(FLOWERS_URL);

        }
        else {

        }

    }



    //Inner Class;
    private class JsonDownload extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... objects) {
            URL url = null;
            try {
                url = new URL(objects[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line  = " ";

                while ((line = reader.readLine()) != null){

                    sb.append(line);

                }

                return sb.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray jsonArray = new JSONArray(s);
                JSONObject flowerObject = jsonArray.getJSONObject(1);
                String flowerName = flowerObject.getString("name");
                //Toast.makeText(MainActivity.this,flowerName, Toast.LENGTH_SHORT).show();
                flowerTV.setText(flowerName);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

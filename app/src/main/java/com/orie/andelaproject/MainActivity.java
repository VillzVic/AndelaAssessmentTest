package com.orie.andelaproject;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    //Variable declaration
    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;
    usersAdapter adapter;
    ArrayList<HashMap<String, String>> arraylist;
    static String USERNAME = "login";
    static String IMAGES = "avatar_url";
    static String USERPAGE = "html_url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //calling the background thread
        new GetContacts().execute();
    }

    //Creating a background thread
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Loading list of Developers", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            arraylist = new ArrayList<HashMap<String, String>>();
            //creating an instance of the http handler class
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://api.github.com/search/users?q=language%3Ajava+location%3Alagos";
            String jsonStr = sh.makeServiceCall(url); //calling makeServiceCall in HttpHandler class
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) { //if data was retreived
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("items");

                    // looping through All Contacts and store their details
                    for (int i = 0; i < contacts.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject c = contacts.getJSONObject(i);
                        map.put("login", c.getString("login"));
                        map.put("avatar_url", (c.getString("avatar_url")));
                        map.put("html_url", c.getString("html_url"));
//
                        // Set the JSON Objects into the array
                        arraylist.add(map);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }
            } else {  // if no data was retreived
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Locate the listview in listview_main.xml
            lv = (ListView) findViewById(R.id.list_views);
            // Pass the results into ListViewAdapter.java
            adapter = new usersAdapter(MainActivity.this, arraylist);
            // Set the adapter to the ListView
            lv.setAdapter(adapter);
        }
    }


}








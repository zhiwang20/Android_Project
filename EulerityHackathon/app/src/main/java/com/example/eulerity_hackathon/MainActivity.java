package com.example.eulerity_hackathon;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.eulerity_hackathon.Adapter.imagesAdapter;
import com.example.eulerity_hackathon.Models.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Image> imageList = new ArrayList<>();
    private static final String URL_STRING = "https://eulerity-hackathon.appspot.com/image";
    RequestQueue requestQueue;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Loading asynchronous images
        LoadImageAsyncTask task = new LoadImageAsyncTask();
        task.execute();
    }

    private class LoadImageAsyncTask extends AsyncTask<Void, Void, ArrayList<Image>> {
        @Override
        protected ArrayList<Image> doInBackground(Void... params) {

            getImageList();
            return imageList;  //pass to onPostExecute
        }

        @Override
        protected void onPostExecute(ArrayList<Image> images) {
            if (images == null) {
                return;
            }
            updateUi(images);
        }

        private void getImageList() {
            requestQueue = Volley.newRequestQueue(getApplicationContext());  //(this)
            JsonArrayRequest imgListRequest = new JsonArrayRequest(Request.Method.GET,
                    URL_STRING,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(LOG_TAG, response.toString());
                            getImageUrls(response);
                        }
                    },
                    error -> {
                        Log.d(LOG_TAG, "Error with JsonArrayRequest: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Error obtaining images", Toast.LENGTH_LONG).show();
                    });
            requestQueue.add(imgListRequest);
        }

        private void getImageUrls(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    String url = jsonObj.getString("url");
                    Image image = new Image(url);
                    imageList.add(image);
                } catch (JSONException e) {
                    Log.d(LOG_TAG, "Error getting JSON object: " + e.getMessage());
                }
            }
        }
    }

    private void updateUi(final ArrayList<Image> images) {
        ListView imageListView =  findViewById(R.id.list);

        imagesAdapter adapter = new imagesAdapter(this, images);

        imageListView.setAdapter(adapter);

        imageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, image_edition.class);
                intent.putExtra("img", images.get(position).getUrl());
                startActivity(intent);
            }
        });
    }
}
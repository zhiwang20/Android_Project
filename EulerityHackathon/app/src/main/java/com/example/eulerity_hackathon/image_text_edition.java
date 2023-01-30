package com.example.eulerity_hackathon;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.eulerity_hackathon.Models.Image;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;


import jp.wasabeef.picasso.transformations.ColorFilterTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;
import jp.wasabeef.picasso.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.ContrastFilterTransformation;

public class image_text_edition extends AppCompatActivity {

    ImageView imageClicked;
    String urlToSend;
    RequestQueue requestQueue;
    static ArrayList<Image> imageList = new ArrayList<>();
    private static final String REQUEST_URL = "https://eulerity-hackathon.appspot.com/upload";

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_text_edition);

        Intent intent = getIntent();

        if (intent.getStringExtra("imageClicked1") != null) {
            imageClicked = (ImageView) findViewById(R.id.imageViewTextEdition);
            Picasso.get().load(intent.getStringExtra("imageClicked1")).transform(new GrayscaleTransformation()).into(imageClicked);
            urlToSend = intent.getStringExtra("imageClicked1");
        } else if (intent.getStringExtra("imageClicked2") != null) {
            imageClicked = (ImageView) findViewById(R.id.imageViewTextEdition);
            Picasso.get().load(intent.getStringExtra("imageClicked2")).transform(new ColorFilterTransformation(Color.parseColor("#539b59b6"))).into(imageClicked);
            urlToSend = intent.getStringExtra("imageClicked2");

        } else if (intent.getStringExtra("imageClicked3") != null) {
            imageClicked = (ImageView) findViewById(R.id.imageViewTextEdition);
            Picasso.get().load(intent.getStringExtra("imageClicked3")).transform(new BrightnessFilterTransformation(imageClicked.getContext(), 0.5f)).into(imageClicked);
            urlToSend = intent.getStringExtra("imageClicked3");

        } else if (intent.getStringExtra("imageClicked4") != null) {
            imageClicked = (ImageView) findViewById(R.id.imageViewTextEdition);
            Picasso.get().load(intent.getStringExtra("imageClicked4")).transform(new BrightnessFilterTransformation(imageClicked.getContext(), -0.5f)).into(imageClicked);
            urlToSend = intent.getStringExtra("imageClicked4");

        } else if (intent.getStringExtra("imageClicked5") != null) {
            imageClicked = (ImageView) findViewById(R.id.imageViewTextEdition);
            Picasso.get().load(intent.getStringExtra("imageClicked5")).transform(new ContrastFilterTransformation(imageClicked.getContext(), 34)).into(imageClicked);
            urlToSend = intent.getStringExtra("imageClicked5");

        } else if (intent.getStringExtra("imageClicked6") != null) {
            imageClicked = (ImageView) findViewById(R.id.imageViewTextEdition);
            Picasso.get().load(intent.getStringExtra("imageClicked6")).transform(new ContrastFilterTransformation(imageClicked.getContext(), -34)).into(imageClicked);
            urlToSend = intent.getStringExtra("imageClicked6");
        }
    }

    public void setTextOnImage(View view) {
        TextView textView = findViewById(R.id.myImageViewText);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButtonPost);
        EditText editText = findViewById(R.id.editTextImage);

        textView.setVisibility(View.VISIBLE);
        floatingActionButton.setVisibility(View.VISIBLE);
        textView.setText(editText.getText().toString());
    }


    public void starClick(View view) {
        image_text_edition.LoadUrl task = new image_text_edition.LoadUrl();
        task.execute();
        Toast.makeText(getApplicationContext(),"Uploading image",Toast.LENGTH_LONG).show();
    }

    public void post(String JSONurl) {

        // appid: a string unique to your app (e.g. your email address)
        // original: the url of the original image the user edited
        // file: the image file being uploaded

        String dataString = "xk6192ed@gmail.com";
        String urlString = urlToSend ;
        OutputStream out = null;
        try {
            URL url = new URL(JSONurl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            out = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(out, "UTF-8"));
            writer.write(dataString);
            writer.write(urlString);
            writer.flush();
            writer.close();
            out.close();
            urlConnection.connect();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Problem on POST", e);
        }
    }

    private class LoadUrl extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void ... params) {
            getImageList();
            Log.d("wang",imageList.get(0).toString());
            return imageList.get(0).toString();  //pass to onPostExecute
        }

        @Override
        protected void onPostExecute(String url) {
            if (url == null) {
                return;
            }
            post(url);
        }

        private void getImageList() {
            requestQueue = Volley.newRequestQueue(getApplicationContext());  //(this)
            JsonArrayRequest imgListRequest = new JsonArrayRequest(Request.Method.GET,
                    REQUEST_URL,
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
                try {
                    JSONObject jSONesponse = new JSONObject((Map) jsonArray);
                    String url = jSONesponse.getString("url");
                    Image image = new Image(url);
                    imageList.add(image);
                } catch (JSONException e) {
                    Log.d(LOG_TAG, "Error getting JSON object: " + e.getMessage());
                }
        }
    }
}
    

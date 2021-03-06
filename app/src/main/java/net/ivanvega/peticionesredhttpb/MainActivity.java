package net.ivanvega.peticionesredhttpb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txt;
    RequestQueue queue;
    ImageView imgview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txtString);
        imgview = findViewById(R.id.imgFoto);

        queue = Volley.newRequestQueue(this);

        findViewById(R.id.btnStringReq).setOnClickListener(
                v -> stringRequest()
        );

        findViewById(R.id.btnJsonReq).setOnClickListener(
                v -> jsonRequest()
        );

        //findViewById(R.id.btnImageReq).setOnClickListener(
        //        v -> imageRequestMethd(),
       // );
        findViewById(R.id.btnImageReq).setOnClickListener(
                v -> {

                    try {
                        imageRequestMethd();
                        imageRequestMethd2();
                        imageRequestMethd3();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                );
    }

    private void imageRequestMethd() throws InterruptedException {

        ImageRequest imageRequest = new ImageRequest(
                "https://s1.1zoom.me/big3/471/Painting_Art_Back_view_Photographer_575380_3840x2400.jpg",

                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        imgview.setImageBitmap(response);
                    }
                },
                300, 300,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ALPHA_8,

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );

        MySingleton.getInstance(this).
                addToRequestQueue(imageRequest);


    }

    private void imageRequestMethd2() {

        ImageRequest imageRequest = new ImageRequest(
                "https://live.staticflickr.com/65535/51307594059_aca75afe2b.jpg",

                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        imgview.setImageBitmap(response);
                    }
                },
                300, 300,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ALPHA_8,

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );


        MySingleton.getInstance(this).
                addToRequestQueue(imageRequest);


    }

    private void imageRequestMethd3() {

        ImageRequest imageRequest = new ImageRequest(
                "https://live.staticflickr.com/65535/51307882275_7479b2422c.jpg",

                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        imgview.setImageBitmap(response);
                    }
                },
                300, 300,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ALPHA_8,

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );


        MySingleton.getInstance(this).
                addToRequestQueue(imageRequest);


    }

    private void jsonRequest() {
        JsonRequest jsonRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://jsonplaceholder.typicode.com/albums",
                null,
                response -> {

                    for(int i=0; i<response.length(); i++){
                        try {
                            JSONObject   jsonObject = response.getJSONObject(i);
                            String album = "";
                            album +="ID: " + jsonObject.getInt("id") + ", Titulo: " +
                           jsonObject.getString("title");
                            Log.d("JSONRequestGIVO", album );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Gson gson = new Gson();

                    Type lsAlbumType = new TypeToken<List<MiAlbum>>(){}.getType();

                    List<MiAlbum> lsMialbun =
                            gson.fromJson(response.toString(), lsAlbumType);

                    for(MiAlbum item: lsMialbun){
                        String album = "";
                        album +="ID: " + item.getId() + ", Titulo: " +
                                item.getTitle();
                        Log.d("JSONRequestGIVOGSON", album );
                    }



                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );
        queue.add(jsonRequest);
    }

    private void stringRequest() {
        // Instantiate the RequestQueue

        String url ="http://www.google.com";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                txt.setText("Response is: "+ response.substring(0,500));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txt.setText("That didn't work!" +  error.toString());
            }
        }
        );
        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }
}
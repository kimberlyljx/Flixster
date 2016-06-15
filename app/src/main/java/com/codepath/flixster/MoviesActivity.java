package com.codepath.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.flixster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MoviesActivity extends AppCompatActivity {
    ArrayList<Movie> boxMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        // 1. Get the actual movies
        String URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray moviesJSON = null;
                try {
                    moviesJSON = response.getJSONArray("results");

                    boxMovies = Movie.fromJson(moviesJSON);
                    Log.d("DEBUG", boxMovies.toString());

                    // 2. Get the ListView we want to populate
                    ListView lvMovies = (ListView) findViewById(R.id.lvMovies); // must cast

                    // 3. Create ArrayAdapter (adapter takes the data and maps it to the view)
                    // ArrayAdapter<Movie> adapter = new ArrayAdapter<Movie>(this,
                    // android.R.layout.simple_list_item_1, movies);
                    MoviesAdapter adapter = new MoviesAdapter(getBaseContext(), boxMovies);

                    // 4. Associate Adapter with the ListView
                    if (lvMovies != null) {
                        lvMovies.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }


}

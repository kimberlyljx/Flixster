package com.codepath.flixster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.flixster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MoviesActivity extends AppCompatActivity {
    ArrayList<Movie> boxMovies;
    MoviesAdapter adapter;

    // Lookup the swipe container view
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    // Get the ListView we want to populate
    @BindView(R.id.lvMovies) ListView lvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);

        // 2. Get the actual movies
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

                    // Create ArrayAdapter (adapter takes the data and maps it to the view)
                    adapter = new MoviesAdapter(getBaseContext(), boxMovies);
                    // Associate Adapter with the ListView
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

        // Invoke Listener
        setupListViewListener();
//
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        String URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Remember to CLEAR OUT old items before appending in the new ones
                adapter.clear();
                JSONArray moviesJSON = null;
                try {
                    moviesJSON = response.getJSONArray("results");
                    boxMovies = Movie.fromJson(moviesJSON);
                    Log.d("DEBUG", boxMovies.toString());

                    // Create ArrayAdapter (adapter takes the data and maps it to the view)
                    adapter = new MoviesAdapter(getBaseContext(), boxMovies);
                    // Associate Adapter with the ListView
                    if (lvMovies != null) {
                        lvMovies.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);

            }
            public void onFailure(Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
        });
    }

    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;

    // for setting up the listener
    private void setupListViewListener() {
        // when short-clicked, taken to the Edit form screen for that item
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // first parameter is the context, second is the class of the activity to launch
                Intent i = new Intent(MoviesActivity.this, MovieDetailActivity.class);
                // pass position, ratings using RatingBar, popularity, and synopsis
                Movie currentMovie = boxMovies.get(position);
                i.putExtra("title", currentMovie.getTitle());
                i.putExtra("backdrop_path", currentMovie.getBackdropPath());
                i.putExtra("overview", currentMovie.getOverview());
                i.putExtra("vote_average", currentMovie.getVote_average());
                i.putExtra("id", currentMovie.getId());
                i.putExtra("position", position);
                startActivity(i);
            }
        });
    }
}

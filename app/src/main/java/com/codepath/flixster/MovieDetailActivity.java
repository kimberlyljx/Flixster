package com.codepath.flixster;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

class MovieDetailActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Get the data
        String movieTitle = getIntent().getStringExtra("title");
        String movieOverview = getIntent().getStringExtra("overview");
        String backdropPath = getIntent().getStringExtra("backdrop_path");
        Double vote_average = getIntent().getDoubleExtra("vote_average", 0.00); // 0.00 default
        int id = getIntent().getExtras().getInt("id");

        // Q: How to findViewById in specific subtree?
        TextView tvTitle = (TextView) findViewById(R.id.tvMovieDetailTitle);
        TextView tvOverview = (TextView) findViewById(R.id.tvMovieDetailOverview);
        RatingBar rbRating = (RatingBar) findViewById(R.id.rbMovieDetailRating);
        ImageView ivPoster = (ImageView) findViewById(R.id.ivMovieDetailPic);

        tvTitle.setText(movieTitle);
        tvOverview.setText(movieOverview);
        float rating = vote_average.floatValue();
        rbRating.setRating(rating) ;


        // 2. Get the actual movies
        String URL = "http://api.themoviedb.org/3/movie/" + id + "/videos" + "?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieVideosJSON = null;
//                try {
//                    movieVideosJSON = response.getJSONArray("results");
//                    JSONObject movieVideo = movieVideosJSON.getJSONObject(0);
//
//
//                    Log.d("DEBUG", movieVideos.toString());
//
//                    // 3. Create ArrayAdapter (adapter takes the data and maps it to the view)
//                    adapter = new MoviesAdapter(getBaseContext(), boxMovies);
//
//                    // 4. Associate Adapter with the ListView
//                    if (lvMovies != null) {
//                        lvMovies.setAdapter(adapter);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

        String imageUri;
        // Loading a remote image thru URL
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageUri = "https://image.tmdb.org/t/p/w342" + backdropPath;
        } else {
            imageUri = "https://image.tmdb.org/t/p/w342" + backdropPath;
        }

        Picasso.with(this).load(imageUri)
                .placeholder(R.drawable.movie_placeholder)
                .into(ivPoster);
    }
}

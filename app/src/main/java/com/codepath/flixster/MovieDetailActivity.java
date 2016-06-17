package com.codepath.flixster;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends YouTubeBaseActivity {
    @BindView(R.id.tvMovieDetailTitle) TextView tvTitle;
    @BindView(R.id.tvMovieDetailOverview) TextView tvOverview;

    @BindView(R.id.ivMovieDetailPic) ImageView ivMovieDetailPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        // Get the data
        String movieTitle = getIntent().getStringExtra("title");
        String movieOverview = getIntent().getStringExtra("overview");
        String backdropPath = getIntent().getStringExtra("backdrop_path");
        Double vote_average = getIntent().getDoubleExtra("vote_average", 0.00); // 0.00 default
        int id = getIntent().getExtras().getInt("id");

//        String imageUri;
//        // Loading a remote image thru URL
//        imageUri = "https://image.tmdb.org/t/p/w342" + backdropPath;
//        Picasso.with(this).load(imageUri)
//                .placeholder(R.drawable.movie_placeholder)
//                .transform(new RoundedCornersTransformation(15, 15))
//                .resize(500, 0)
//                .into(ivMovieDetailPic);

        // Loading a remote image thru URL
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            tvTitle.setText(movieTitle);
            tvOverview.setText(movieOverview);
            float rating = vote_average.floatValue();
            RatingBar rbRating = (RatingBar) findViewById(R.id.rbMovieDetailRating);
            rbRating.setRating(rating) ;
        }

        // 2. Get the actual movies
        String URL = "http://api.themoviedb.org/3/movie/" + id + "/videos" + "?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient clientMovie = new AsyncHttpClient();
        clientMovie.get(URL, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                JSONArray movieVideosJSON = null;
                try {
                    movieVideosJSON = response.getJSONArray("results");
                    final JSONObject movieVideo = movieVideosJSON.getJSONObject(0);
                    Log.d("DEBUG", movieVideo.toString());
                    YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);

                    youTubePlayerView.initialize("AIzaSyDoJBDU7XVpoLPK8J_NaPlj1ZwAzmu363E",
                            new YouTubePlayer.OnInitializedListener() {
                                @Override
                                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                    YouTubePlayer youTubePlayer, boolean b) {
                                    // do any work here to cue video, play video, etc.
                                    try {
                                        youTubePlayer.cueVideo(movieVideo.getString("key"));
                                        Log.d("DEBUG", movieVideo.getString("key"));
                                    } catch (JSONException e) {
                                        Log.d("DEBUG", "No movie string");
                                    }
                                }
                                @Override
                                public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                    YouTubeInitializationResult youTubeInitializationResult) {
                                    Log.d("DEBUG", "Failed to initialize");
                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", "Player error: " + throwable.toString());
            }

        });
    }
}

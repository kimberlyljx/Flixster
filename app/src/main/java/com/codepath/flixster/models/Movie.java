package com.codepath.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by klimjinx on 6/15/16.
 */
public class Movie {


    private String title;
    private String posterPath;
    private String overview;
    private String backdropPath;
    private double popularity;
    private double vote_average;
    private int id;

    public int getId() {
        return id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() { return overview; }

    public Movie(JSONObject jsonObject) throws JSONException{
        this.title = jsonObject.getString("title");
        this.posterPath = jsonObject.getString("poster_path");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.popularity = jsonObject.getDouble("popularity");
        this.id = jsonObject.getInt("id");
        this.vote_average = jsonObject.getDouble("vote_average");
    }

    // Decodes array of movies json results into movie model objects
    public static ArrayList<Movie> fromJson(JSONArray jsonArray) {
        JSONObject boxMovieJson;
        ArrayList<Movie> boxMovies = new ArrayList<Movie>(jsonArray.length());

        // Process each result in json array, decode and convert to business object
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                boxMovieJson = jsonArray.getJSONObject(i);
                boxMovies.add(new Movie(boxMovieJson));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return boxMovies;
    }

    // Every java class has a toString by default. The error u saw was because the default toString
    // method return those
    @Override
    public String toString() {
        return title + " - " + overview;
    }
}

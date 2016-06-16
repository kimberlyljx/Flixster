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
    private String poster_path;
    private String overview;
    private String backdrop_path;

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() { return overview; }

    public Movie(JSONObject jsonObject) throws JSONException{
        this.title = jsonObject.getString("title");
        this.poster_path = jsonObject.getString("poster_path");
        this.overview = jsonObject.getString("overview");
        this.backdrop_path = jsonObject.getString("backdrop_path");
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

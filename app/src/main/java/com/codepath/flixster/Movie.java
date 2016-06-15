package com.codepath.flixster;

import java.util.ArrayList;

/**
 * Created by klimjinx on 6/15/16.
 */
public class Movie {
    public String title;
    public String posterURL;
    public int rating;

    public Movie(String title, String posterURL, int rating) {
        this.title = title;
        this.posterURL = posterURL;
        this.rating = rating;
    }

    public static ArrayList<Movie> getFakeMovies() {

        // Why do we not specify movie in second <>?
        ArrayList<Movie> movies = new ArrayList<>();

        for (int i = 0; i < 60; i++) {
            movies.add(new Movie("The Social Network", "", 75));
            movies.add(new Movie("The Internship", "", 50));
            movies.add(new Movie("The Lion King", "", 100));
        }

        return movies;
    }

    // Every java class has a toString by default. The error u saw was because the default toString
    // method return those
    @Override
    public String toString() {
        return title + " - " + rating;
    }
}

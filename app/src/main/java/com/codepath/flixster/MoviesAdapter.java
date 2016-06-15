package com.codepath.flixster;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flixster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by klimjinx on 6/15/16.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        // K: params are context, resource layout, data
        super(context, R.layout.item_movie, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Movie movie = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
        }

        // Lookup view for data population
        // K: convertView.findViewById take the whole xml thing and find the thing in it
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
        ImageView ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);

        // clear out image from convert view
        ivPoster.setImageResource(0);

        // Populate the data into the template view using the data object
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        // Loading a remote image thru URL
        String imageUri = "https://image.tmdb.org/t/p/w342" + movie.getPoster_path();
        Picasso.with(getContext()).load(imageUri).into(ivPoster);

        // debug log, and param is 1)string for easy finding 2)what to print out
        Log.d("MoviesAdapter", "Position: " + position);

        // Return the completed view to render on screen
        return convertView;
    }
}

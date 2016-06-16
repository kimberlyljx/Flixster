package com.codepath.flixster;

import android.content.Context;
import android.content.res.Configuration;
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

    // View Lookup Cache
    private static class ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
    }

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        // K: params are context, resource layout, data
        super(context, R.layout.item_movie, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Movie movie = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            // Lookup view for data population
            // K: convertView.findViewById take the whole xml thing and find the thing in it
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
            viewHolder.ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // clear out image from convert view
        viewHolder.ivPoster.setImageResource(0);

        // Populate the data into the template view using the data object
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        String imageUri;
        // Loading a remote image thru URL
        //
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageUri = "https://image.tmdb.org/t/p/w342" + movie.getBackdrop_path();
        } else {
            imageUri = "https://image.tmdb.org/t/p/w342" + movie.getPoster_path();
        }

        Picasso.with(getContext()).load(imageUri).into(viewHolder.ivPoster);

        // debug log, and param is 1)string for easy finding 2)what to print out
        Log.d("MoviesAdapter", "Position: " + position);

        // Return the completed view to render on screen
        return convertView;
    }
}

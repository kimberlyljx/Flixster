package com.codepath.flixster;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flixster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

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

    // Returns the number of types of Views that will be created by getView(int, View, ViewGroup)
    @Override
    public int getViewTypeCount() {
        // Returns the number of types of Views that will be created by this adapter
        // Each type represents a set of views that can be converted
        return 2; // only good_vote or not
    }

    // Get the type of View that will be created by getView(int, View, ViewGroup)
    // for the specified item.
    @Override
    public int getItemViewType(int position) {
        if (this.getItem(position).getVote_average() > 5) {
            return 0; // good rating
        } else {
            return 1; // bad rating
        }
    }

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        // K: params are context, resource layout, data
        super(context, R.layout.item_movie, movies);
    }

    // Given the item type, responsible for returning the correct inflated XML layout file
    private View getInflatedLayoutForType(int type) {
        if (type == 0 ) { // good rating
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie_popular, null);
        } else {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, null);
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item type for this position
        int viewType = this.getItemViewType(position);
        Movie movie;
        String imageUri;
        ViewHolder viewHolder; // view lookup cache stored in tag

        switch(viewType)
        {
            case 0:
                movie = getItem(position); // Get the data item for this position

                // Check if an existing view is being reused, otherwise inflate the view
                if (convertView == null) {
                    // Inflate XML layout based on the type
                    convertView = getInflatedLayoutForType(viewType);
                    viewHolder = new ViewHolder();

                    // Lookup view for data population
                    viewHolder.ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                // clear out image from convert view
                viewHolder.ivPoster.setImageResource(0);

                // Loading a remote image thru URL
                imageUri = "https://image.tmdb.org/t/p/w342" + movie.getBackdropPath();
                Picasso.with(getContext()).load(imageUri)
                        .transform(new RoundedCornersTransformation(15, 15))
                        .placeholder(R.drawable.movie_placeholder)
                        .into(viewHolder.ivPoster);

                // return the created view
                return convertView;

            case 1:
                // Get the data item for this position
                movie = getItem(position);

                // Check if an existing view is being reused, otherwise inflate the view
                if (convertView == null) {
                    // Inflate XML layout based on the type
                    convertView = getInflatedLayoutForType(viewType);
                    viewHolder = new ViewHolder();

                    // Lookup view for data population
                    viewHolder.ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
                    viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                    viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                // clear out image from convert view
                viewHolder.ivPoster.setImageResource(0);
                // Populate the data into the template view using the data object
                viewHolder.tvTitle.setText(movie.getTitle());
                viewHolder.tvOverview.setText(movie.getOverview());

                // Loading a remote image thru URL
                if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    imageUri = "https://image.tmdb.org/t/p/w342" + movie.getBackdropPath();
                } else {
                    imageUri = "https://image.tmdb.org/t/p/w342" + movie.getPosterPath();
                }
                // clear out image from convert view
                viewHolder.ivPoster.setImageResource(0);
                Picasso.with(getContext()).load(imageUri)
                        .placeholder(R.drawable.movie_placeholder)
                        .transform(new RoundedCornersTransformation(15, 15))
                        .into(viewHolder.ivPoster);

                // return the created view
                return convertView;

            default:
                //Throw exception, unknown data type
                throw new UnknownError("Cannot determine type");
        }
    }
}

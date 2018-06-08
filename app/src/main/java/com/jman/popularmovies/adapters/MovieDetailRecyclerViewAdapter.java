package com.jman.popularmovies.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.jman.popularmovies.Activity_detail;
import com.jman.popularmovies.Models.Review;
import com.jman.popularmovies.Models.Trailer;
import com.jman.popularmovies.MovieResults;
import com.jman.popularmovies.R;
import com.jman.popularmovies.data.FavouriteMoviesContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 25/05/2018.
 *
 * convert an object at a position into a list row item to be inserted
 */

public class MovieDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = MovieDetailRecyclerViewAdapter.class.getSimpleName();

    //private ArrayList<MovieResults.Movie> items = MovieResults.getListOfMovies();
    private final int DETAILS = 0, REVIEWS = 1, TRAILERS = 2;

    private MovieResults.Movie movie;

    // the list of review json objects represented as Java Movie objects
    private ArrayList<Review> reviews;


    // the list of review json objects represented as Java Movie objects
    private ArrayList<Trailer> trailers;

    private Context mContext;

    public MovieDetailRecyclerViewAdapter(MovieResults.Movie movie, ArrayList<Review> reviews, ArrayList<Trailer> trailers, Context context) {
        this.movie = movie;
        this.reviews = reviews;
        this.trailers = trailers;
        this.mContext = context; // important so the adapter knows which activity it is attached to
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        switch (viewType) {
            case DETAILS:
                View movieDetailView = inflater.inflate(R.layout.movie_detail_list_item, parent, false);
                viewHolder = new MovieDetailViewHolder(movieDetailView);
                break;
            case REVIEWS:
                View movieReviewView = inflater.inflate(R.layout.review_list_item, parent, false);
                viewHolder = new MovieReviewViewHolder(movieReviewView);
                break;
            case TRAILERS:
                View movieTrailerView = inflater.inflate(R.layout.movie_trailer_list_item, parent, false);
                viewHolder = new MovieTrailerViewHolder(movieTrailerView);
//            default:
//                View v = inflater.inflate(R.layout.simple_list_item, parent, false);
//                viewHolder = new SimpleViewHolder(v);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case DETAILS:
                MovieDetailViewHolder detailsVH = (MovieDetailViewHolder) holder;
                detailsVH.bindMovieDetailViews(mContext);
                Log.d(TAG, "item count " + getItemCount());
                Log.d(TAG, "onBindViewHolder for DETAILS Position: " + position);
                break;
            case REVIEWS:
                MovieReviewViewHolder reviewsVH = (MovieReviewViewHolder) holder;
                reviewsVH.bindMovieReviewViews(position - 1);
                Log.d(TAG, "item count " + getItemCount());
                Log.d(TAG, "onBindViewHolder for REVIEWS Position: " + position);
                break;
            case TRAILERS:
                MovieTrailerViewHolder trailersVH = (MovieTrailerViewHolder) holder;
                trailersVH.bindMovieTrailerViews(position - (1 + reviews.size()));
                Log.d(TAG, "onBindViewHolder for TRAILERS Position: " + position);
                break;
//            default:
//                SimpleViewHolder vh = (SimpleViewHolder) holder;
//                vh.bindViews();
//                break;
        }
    }

    @Override
    public int getItemCount() {
        return 1 + reviews.size() + trailers.size(); //1 movie detail + 8 reviews + 7 trailers
    }

    /*
    * determine the order at which the itemviews will be displayed*/
    @Override
    public int getItemViewType(int position) {

        // top of the recyclerview list of viewholders
        if (position == 0) {
            return DETAILS;
        }
        else if (position > 0 && position <= reviews.size()) { //if between 1 and the number of reviews inflate review views after movie details
            return REVIEWS;
        } else {
            return TRAILERS;
        }
        //return -1;
    }

    public void updateReviewsUI(ArrayList<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public void updateTrailersUI(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    class MovieDetailViewHolder extends RecyclerView.ViewHolder {

        private final static String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";

        private String addToFavouritesText = "Add to Favourites";
        private String removeFromFavouritesText = "Remove from Favourites";

        ImageView moviePoster;
        Button favouritesButton;
        TextView title;
        TextView releaseDate;
        TextView overview;
        TextView popularity;
        TextView voteAverage;

        public MovieDetailViewHolder(View itemView) {
            super(itemView);

            moviePoster = itemView.findViewById(R.id.movie_detailView_poster);
            favouritesButton = itemView.findViewById(R.id.add_to_favourites_button);
            title = itemView.findViewById(R.id.movie_title);
            releaseDate = itemView.findViewById(R.id.movie_release_date);
            overview = itemView.findViewById(R.id.movie_overview);
            popularity = itemView.findViewById(R.id.movie_popularity);
            voteAverage = itemView.findViewById(R.id.movie_vote_average);

            setButtonText();
            favouritesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(favouritesButton.getText().equals(addToFavouritesText)) {
                        // add to favourites method
                        addToFavouritesList();
                    }

                    if(favouritesButton.getText().equals(removeFromFavouritesText)) {
                        // removeFromfavourites method
                        removeFromFavouritesList();
                    }

                    setButtonText();
                }
            });

            //set onclick listener on button
            // getText()
            // if add to favourites is the text, invoke add to favourites method
            // if remove from favourites is the text, invoke delete from favourites method

        }

        public void bindMovieDetailViews(Context context) {
            Picasso
                    .with(context)
                    .load(MOVIE_POSTER_BASE_URL + movie.getPosterPath())
                    .fit()
                    .into(moviePoster);

            title.setText(movie.getTitle());
            releaseDate.setText(movie.getReleaseDate());
            overview.setText(movie.getOverview());
            popularity.setText(movie.getPopularity());
            voteAverage.setText(movie.getVoteAverage());

        }


        public void setButtonText() {
            // query db to find is movie id is in the table
            String movieId = movie.getId();
            String mSelection = "movieId=?";
            String[] mSelectionArgs = new String[]{movieId};

            Cursor retCursor;

          retCursor =  mContext.getContentResolver().query(FavouriteMoviesContract.FavouriteMovieEntry.CONTENT_URI,
                    null,
                    mSelection,
                    mSelectionArgs,
                    null

            );
            // if movie not in db, button.setText("Add to Favourites")

            if(retCursor.getCount() <= 0) {
                favouritesButton.setText(addToFavouritesText);
            }
            // if movie in db already, button.setText(Remove from favourites)
            else if(retCursor.getCount() > 0) {
                favouritesButton.setText(removeFromFavouritesText);
            }

        }

        /**
         * This method is called when user clicks on the Add to favourites button
         *
         *
         */
        public void addToFavouritesList() {

            // get the id and title of movie
            String movieId = movie.getId();
            String movieTitle = movie.getTitle();

            // Create new empty ContentValues object
            ContentValues contentValues = new ContentValues();

            contentValues.put(FavouriteMoviesContract.FavouriteMovieEntry.COLUMN_NAME_MOVIE_ID, movieId);
            contentValues.put(FavouriteMoviesContract.FavouriteMovieEntry.COLUMN_NAME_TITLE, movieTitle);

            Uri uri = mContext.getContentResolver().insert(FavouriteMoviesContract.FavouriteMovieEntry.CONTENT_URI, contentValues);

            // if URI exists, show uri in toast
            if(uri != null) {
                Toast.makeText(mContext, uri.toString(), Toast.LENGTH_LONG).show();
            }
        }


        public void removeFromFavouritesList() {
            String movieId = movie.getId();
            String mSelection = "movieId=?";
            String[] mSelectionArgs = new String[]{movieId};

            mContext.getContentResolver().delete(FavouriteMoviesContract.FavouriteMovieEntry.CONTENT_URI,
                    mSelection,
                    mSelectionArgs
            );

            Toast.makeText(mContext, "Movie removed", Toast.LENGTH_LONG).show();
        }
    } // end of viewholder class

    class MovieReviewViewHolder extends RecyclerView.ViewHolder {

        private TextView author;
        private TextView content;

        public MovieReviewViewHolder(View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.review_author);
            content = itemView.findViewById(R.id.review_content);

        }

        public void bindMovieReviewViews(int position) {
            // bind views
            author.setText(reviews.get(position).getAuthor());
            content.setText(reviews.get(position).getContent());
        }
    } // end of class

    class MovieTrailerViewHolder extends RecyclerView.ViewHolder {

        private TextView videoType;
        private Button playButton;

        String videoURL = "http://www.youtube.com/watch?v=";
        String videoId;

        public MovieTrailerViewHolder(View itemView) {
            super(itemView);

            videoType = itemView.findViewById(R.id.videoTypeTextView);
            playButton = itemView.findViewById(R.id.playButton);

            //create on clicklistener
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // set the url and open youtube intent
                    videoURL = videoURL + videoId;
                    Uri uri = Uri.parse(videoURL);

                    // With this line the Youtube application, if installed, will launch immediately.
                    // Without it you will be prompted with a list of the application to choose.
                    uri = Uri.parse("vnd.youtube:"  + uri.getQueryParameter("v"));

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(intent);
                }
            });
        }

        public void bindMovieTrailerViews(int position) {
            // bind views
            videoType.setText(trailers.get(position).getType());
            videoId = trailers.get(position).getKey();
        }
    } // end of class

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        private TextView error;

        public SimpleViewHolder(View itemView) {
            super(itemView);

            error = itemView.findViewById(R.id.text1);
        }

        public void bindViews() {
          //  error.setText("Nothing to show :(");
        }
    } // end of class
}

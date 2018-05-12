package com.jman.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jman.popularmovies.data.FavouriteMoviesContract;

/**
 * Created by Justin on 03/05/2018.
 */


public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder> {

    // Holds on to the cursor to display the waitlist
    private Cursor mCursor;
    private Context mContext;

    public FavouritesAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public FavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.favourite_list_item, parent, false);
        return new FavouritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouritesViewHolder holder, int position) {
        // to-do
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        String id = mCursor.getString(mCursor.getColumnIndex(FavouriteMoviesContract.FavouriteMovieEntry.COLUMN_NAME_MOVIE_ID));

        String title = mCursor.getString(mCursor.getColumnIndex(FavouriteMoviesContract.FavouriteMovieEntry.COLUMN_NAME_TITLE));

        // Display the id
        holder.idTextView.setText(id);

        // Display the title
        holder.nameTextView.setText(title);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    /**
     * Swaps the Cursor currently held in the adapter with a new one
     * and triggers a UI refresh
     *
     * @param newCursor the new cursor that will replace the existing one
     */
    public Cursor swapCursor(Cursor newCursor) {

        // Always close the previous mCursor first
        if (mCursor != null) {
            mCursor.close();
        }
        if (mCursor == newCursor) {
            return null; // bc nothing has changed
        }

        // Update the local mCursor to be equal to newCursor
        this.mCursor = newCursor;

        // check if cursor is valid
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
        return mCursor;
    }

    class FavouritesViewHolder extends RecyclerView.ViewHolder {

        TextView idTextView;
        TextView nameTextView;

        public FavouritesViewHolder(View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.movie_id);
            nameTextView = itemView.findViewById(R.id.name_text_view);
        }
    }

}

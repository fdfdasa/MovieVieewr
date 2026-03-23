package com.example.learn;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieViewHolder extends RecyclerView.ViewHolder{
    private ImageView posterImageView;
    private TextView titleTextView;
    private TextView yearTextView;
    private TextView genreTextView;
    private TextView posterTextView;


    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        posterImageView = itemView.findViewById(R.id.posterImageView);
        posterTextView = itemView.findViewById(R.id.posterTextView);
        titleTextView = itemView.findViewById(R.id.titleTextView);
        yearTextView = itemView.findViewById(R.id.yearTextView);
        genreTextView = itemView.findViewById(R.id.genreTextView);
    }

    public void bind(Movie movie) {
        titleTextView.setText("Name: " + movie.getTitle());
        genreTextView.setText("Genre: " + movie.getGenre());

        if (movie.getYear() != null) {
            yearTextView.setText("Year: " + String.valueOf(movie.getYear()));
        } else {
            yearTextView.setText("Year: N/A");
        }

        if (movie.getPosterResource() != null) {
            posterTextView.setText("Poster id: " + movie.getPosterResource());
        } else {
            posterTextView.setText("N/A");
        }

    }
}
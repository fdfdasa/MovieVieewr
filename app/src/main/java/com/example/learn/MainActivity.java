package com.example.learn;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learn.ErrorHandler;
import com.example.learn.JsonUtils;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView movieRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieRecyclerView = findViewById(R.id.movieRecyclerView);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadMovieData();
    }

    private void loadMovieData() {
        try {
            List<Movie> movies = JsonUtils.loadMoviesFromJson(this);
            movieRecyclerView.setAdapter(new MovieAdapter(movies));
        } catch (Exception e) {
            // Send the error to the centralized handler
            ErrorHandler.handleError(this, e, "Failed to load movie database.");
        }
    }
}
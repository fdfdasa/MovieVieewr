package com.example.learn;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView movieRecyclerView;
    private MovieAdapter adapter;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerView();
        loadMovieData();
    }

    public void setupRecyclerView() {
        movieRecyclerView = findViewById(R.id.movieRecyclerView);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadMovieData() {
        try {
            movies = JsonUtils.loadMoviesFromJson(this);
            if (movies.isEmpty()) {
                showError("No movie data found. Please check JSON file.");
            } else {
                adapter = new MovieAdapter(movies);
                movieRecyclerView.setAdapter(adapter);
            }
        } catch (Exception e) {
            showError("An unexpected error occurred: " + e.getMessage());
            }
        }

    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
package com.example.learn;

import java.util.List;

public class JsonResult {
    public List<Movie> movies;
    public String errorMessage;

    public JsonResult(List<Movie> movies, String errorMessage) {
        this.movies = movies;
        this.errorMessage = errorMessage;
    }
}

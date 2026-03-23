package com.example.learn;

import android.content.Context;
import android.util.Log;
import com.example.learn.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = "JsonUtils";

    public static List<Movie> loadMoviesFromJson(Context context) throws Exception {
        ArrayList<Movie> movies = new ArrayList<>();

        // Load from assets folder as per your original file structure
        InputStream is = context.getAssets().open("movies.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        is.close();

        JSONArray jsonArray = new JSONArray(sb.toString());
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject movieObj = jsonArray.getJSONObject(i);

                // 1. Title Validation
                String title = movieObj.optString("title", "MISSING VALUE");

                // 2. Year Validation (Strict Type Checking)
                int year = -1;
                if (movieObj.has("year") && !movieObj.isNull("year")) {
                    Object value = movieObj.get("year");
                    if (!(value instanceof Integer)) {
                        throw new JSONException("Year must be an Integer");
                    }
                    year = (int) value;
                    if (year < 0) throw new JSONException("Year is negative");
                }

                // 3. Genre & Poster Validation
                String genre = movieObj.optString("genre", "MISSING VALUE");
                String poster = movieObj.optString("poster", "MISSING VALUE");

                // Check for empty objects {}
                if (title.equals("MISSING VALUE") && year == -1) {
                    throw new JSONException("Empty item");
                }

                movies.add(new Movie(title, year, genre, poster));

            } catch (JSONException e) {
                Log.e(TAG, "Skipping invalid movie at index " + i + ": " + e.getMessage());
            }
        }
        return movies;
    }
}
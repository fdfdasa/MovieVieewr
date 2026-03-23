package com.example.learn;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static JsonResult loadMoviesFromJson(Context context) {
        List<Movie> movies = new ArrayList<>();

        try {
            InputStream is;

            // FILE NOT FOUND
            try {
                is = context.getAssets().open("movies.json");
            } catch (IOException e) {
                return new JsonResult(null, "File not found: movies.json");
            }

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");

            JSONArray jsonArray;

            // JSON PARSING ERROR
            try {
                jsonArray = new JSONArray(json);
            } catch (Exception e) {
                return new JsonResult(null, "Invalid JSON format");
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.optJSONObject(i);

                if (object.length() == 0) continue;

                // MISSING FIELDS
                String title = object.optString("title", null);
                String genre = object.optString("genre", null);
                String poster = object.optString("poster", "N/A");

// Handle missing or null values
                if (title == null || title.equals("null") || title.isEmpty()) {
                    title = "N/A";
                }

                if (genre == null || genre.equals("null") || genre.isEmpty()) {
                    genre = "N/A";
                }

                Integer year = null;

                //INVALID FORMAT
                if (object.has("year")) {
                    try {
                        String yearStr = object.getString("year");

                        // Only allow whole numbers (no text, no decimals)
                        if (yearStr.matches("\\d+")) {
                            year = Integer.parseInt(yearStr);

                            // Optional: reject negative or unrealistic values
                            if (year < 0) {
                                year = null;
                            }
                        } else {
                            year = null; // invalid format → set null
                        }

                    } catch (Exception e) {
                        year = null; // any error → set null
                    }
                }

                movies.add(new Movie(title, year, genre, poster));
            }

        } catch (IOException e) {
            return new JsonResult(null, "Error reading file");
        }

        return new JsonResult(movies, null);
    }

    public static void handleJsonException(Exception e) {
        android.util.Log.e("JsonUtils", "Data parsing error: " + e.getMessage());
    }
}
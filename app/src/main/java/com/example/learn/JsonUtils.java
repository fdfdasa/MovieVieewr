package com.example.learn;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static List<Movie> loadMoviesFromJson(Context context) {
        List<Movie> movies = new ArrayList<>();
//      Load movies from JSON
        try {

            InputStream is = context.getAssets().open("movies.json");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.optJSONObject(i);
                if (object.length() == 0) continue;

                String title = object.optString("title", "N/A");
                String genre = object.optString("genre", "N/A");
                String poster = object.optString("poster", "N/A");

                Integer year = null;
                if (object.has("year")) {
                    try {
                        year = Integer.parseInt(object.optString("year"));
                        if (year < 0) year = null;
                    } catch (Exception e) {
                        handleJsonException(e);
                    }
                }

                movies.add(new Movie(title, year, genre, poster));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static void handleJsonException(Exception e) {
        android.util.Log.e("JsonUtils", "Data parsing error: " + e.getMessage());
    }
}

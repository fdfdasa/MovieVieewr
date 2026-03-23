package com.example.learn;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ErrorHandler {
    private static final String TAG = "ErrorHandler";

    /**
     * Handles common errors and shows specific user-facing messages.
     */
    public static boolean handleError(@NonNull Context context, @Nullable Throwable throwable, String fallbackMessage) {
        if (throwable == null) {
            showError(context, fallbackMessage);
            return true;
        }

        Log.e(TAG, "Error occurred: " + throwable.getMessage(), throwable);

        if (throwable instanceof FileNotFoundException) {
            showError(context, "Required data file was not found.");
            return true;
        } else if (throwable instanceof JSONException) {
            showError(context, "Data format is invalid. Skipping corrupted entries.");
            return true;
        } else if (throwable instanceof IOException) {
            showError(context, "Error accessing movie data. Please try again.");
            return true;
        } else if (throwable instanceof Resources.NotFoundException) {
            showError(context, "Required resource was not found.");
            return true;
        }

        showError(context, fallbackMessage);
        return false;
    }

    public static void showError(@NonNull Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Safely gets a drawable resource ID by name.
     */
    public static int getDrawableResourceId(Context context, String resourceName, int defaultResourceId) {
        try {
            // FIX: Changed .equals(null) to == null to prevent NullPointerException
            if (resourceName == null || resourceName.isEmpty()) {
                return defaultResourceId;
            }

            int resourceId = context.getResources().getIdentifier(
                    resourceName, "drawable", context.getPackageName());

            if (resourceId == 0) {
                return defaultResourceId;
            }
            return resourceId;
        } catch (Exception e) {
            Log.e(TAG, "Error finding resource: " + resourceName, e);
            return defaultResourceId;
        }
    }
}
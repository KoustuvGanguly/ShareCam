package com.tweekerz.sharecam;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;

public class CameraSettingPreferences {
    private static final String FLASH_MODE = "squarecamera__flash_mode";

    private static SharedPreferences getCameraSettingPreferences(@NonNull Context context) {
        return context.getSharedPreferences("com.desmond.squarecamera", 0);
    }

    protected static void saveCameraFlashMode(@NonNull Context context, @NonNull String cameraFlashMode) {
        SharedPreferences preferences = getCameraSettingPreferences(context);
        if (preferences != null) {
            Editor editor = preferences.edit();
            editor.putString(FLASH_MODE, cameraFlashMode);
            editor.apply();
        }
    }

    protected static String getCameraFlashMode(@NonNull Context context) {
        SharedPreferences preferences = getCameraSettingPreferences(context);
        if (preferences != null) {
            return preferences.getString(FLASH_MODE, "auto");
        }
        return "auto";
    }
}

package com.tweekerz.sharecam;

import android.os.Bundle;

/**
 * Created by Koustuv Ganguly on 04-Sep-16.
 */
public class Common {


    public static int convertToInt(String arg) {
        int returnInt = 0;
        try {
            returnInt = Integer.parseInt(arg);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return returnInt;
    }

    public static long convertToLong(String arg) {
        long returnLong = 0;
        try {
            returnLong = Long.parseLong(arg);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return returnLong;
    }

    public static double convertToDouble(String arg) {
        double returnInt = 0.0d;
        try {
            returnInt = Double.parseDouble(arg);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return returnInt;
    }

    public static boolean isNullOrEmpty(String args) {
        if (args == null) {
            return true;
        }
        try {
            if (args.trim().length() > 0) {
                return false;
            }
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return true;
        }
    }

    public static Bundle getmStoringBundle() {
        return mStoringBundle;
    }

    public static LandingActivity getmLandingActivity() {
        return mLandingActivity;
    }

    public static void setmLandingActivity(LandingActivity landingActivity) {
        mLandingActivity = landingActivity;
    }

    public static LandingActivity mLandingActivity;

    public static void setmStoringBundle(Bundle storingBundle) {
        mStoringBundle = storingBundle;
    }

    public static Bundle mStoringBundle;
}

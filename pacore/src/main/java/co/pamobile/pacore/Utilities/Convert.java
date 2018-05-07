package co.pamobile.pacore.Utilities;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by Dev04 on 9/28/2016.
 */
public class Convert {
    public static float convertPixelsToDp(float px){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

}

package application.davidelmn.swipetodeleteundorecyclerviewlibrary.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import application.davidelmn.swipetodeleteundorecyclerviewlibrary.R;

/**
 * Created by davide-syn on 9/29/17.
 */

public class ColorUtils {
    public static Drawable getBackgroundColorDrawable(Context context) {
        //mv to config - color
        return new ColorDrawable(ContextCompat.getColor(context, R.color.md_red_400));
    }
}

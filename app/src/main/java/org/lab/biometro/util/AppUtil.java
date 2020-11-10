package org.lab.biometro.util;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;

import org.lab.biometro.R;

public class AppUtil {

    public static void showOtherActivity (Context context, Class<?> cls, int direction) {
        Intent myIntent = new Intent(context, cls);
        ActivityOptions options;
        switch (direction) {
            case 0:
                options = ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, R.anim.slide_out_left);
                context.startActivity(myIntent, options.toBundle());
                break;
            case 1:
                options = ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_left, R.anim.slide_out_right);
                context.startActivity(myIntent, options.toBundle());
                break;
            default:
                context.startActivity(myIntent);
                break;
        }
    }

}

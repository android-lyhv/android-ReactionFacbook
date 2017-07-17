package com.hovanly.dut.reactionfacebook;

import android.content.res.Resources;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 14/07/2017
 */
public class DisplayUtils {
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}

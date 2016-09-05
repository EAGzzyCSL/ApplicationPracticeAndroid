package com.bit.schoolcomment.util;

import com.bit.schoolcomment.MyApplication;

public class DimensionUtil {

    private static float sDensity;

    static {
        sDensity = MyApplication.getDefault().getResources().getDisplayMetrics().density;
    }

    public static int Dp2Px(float dp) {
        return (int) (dp * sDensity + 0.5f);
    }

    public static int Px2Dp(float px) {
        return (int) (px / sDensity + 0.5f);
    }
}

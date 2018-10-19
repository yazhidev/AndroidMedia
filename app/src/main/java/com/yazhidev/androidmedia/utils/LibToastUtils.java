package com.yazhidev.androidmedia.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zengyazhi on 2018/10/19.
 */

public class LibToastUtils {

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}

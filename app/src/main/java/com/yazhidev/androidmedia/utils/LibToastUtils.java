package com.yazhidev.androidmedia.utils;

import android.content.Context;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by zengyazhi on 2018/10/19.
 */

public class LibToastUtils {

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static void showToast(String msg) {
        if(!Thread.currentThread().getName().contains("main")) {
            Observable.just(msg)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }
}

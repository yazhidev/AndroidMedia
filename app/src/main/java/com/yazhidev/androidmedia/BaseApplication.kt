package com.yazhidev.androidmedia

import android.app.Application
import com.yazhidev.androidmedia.utils.LibToastUtils

/**
 * Created by zengyazhi on 2018/10/19.
 */
class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        LibToastUtils.init(this)
    }
}
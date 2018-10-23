package com.yazhidev.androidmedia.utils;

import com.yazhidev.androidmedia.utils.bean.WaveHeader;

/**
 * Created by zengyazhi on 2018/10/23.
 */

public interface ReadWaveHeaderCallback {
    void onSuc(WaveHeader header);

    void onFailure(String msg);
}

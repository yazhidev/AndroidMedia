package com.yazhidev.androidmedia.utils;

import com.yazhidev.androidmedia.utils.bean.WaveHeader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zengyazhi on 2018/10/23.
 */

public class DecoderUtils {

    public static WaveHeader readWavHeader(String wavFilePath) {
        if (!wavFilePath.contains(".wav")) {
            LibToastUtils.showToast("请选择 wav 文件!");
            return null;
        }
        Observable.just(wavFilePath)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        FileInputStream fileInputStream = new FileInputStream(new File(s));
                        BufferedInputStream dis = new BufferedInputStream(fileInputStream);
//                        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(s))));
                        String s1 = readString(dis, 4);
                        int i = readInt(dis);
                        String s2 = readString(dis, 4);
                        String s3 = readString(dis, 4);
                        int i1 = readInt(dis);
                        short i2 = readShort(dis); // typeW
                        fileInputStream.close();
                    }
                });
        return null;
    }

    private static String readString(InputStream inputStream, int length) {
        byte[] bytes = new byte[length];
        try {
            inputStream.read(bytes);
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }aa


    private static short readShort(InputStream inputStream) {
        byte[] bytes = new byte[2];
        try {
            inputStream.read(bytes);
            //{1, 0}
            return (short) (bytes[0] | (bytes[1] << 8));
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static int readInt(InputStream inputStream) {
        byte[] bytes = new byte[4];
        try {
            inputStream.read(bytes);
//            byte[] reverse = reverse(bytes);
            return (int) (bytes[0] | (bytes[1] << 8) | (bytes[2] << 16) | (bytes[3] << 24));
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static byte[] reverse(byte[] original) {
        byte[] result = new byte[original.length];
        for (int i = 0; i < original.length; i++) {
            result[i] = original[original.length - 1 - i];
        }
        return result;
    }
}

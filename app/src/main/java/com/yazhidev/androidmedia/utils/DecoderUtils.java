package com.yazhidev.androidmedia.utils;

import com.yazhidev.androidmedia.utils.bean.WaveHeader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zengyazhi on 2018/10/23.
 */

public class DecoderUtils {

    public static void readWavHeader(String wavFilePath, final ReadWaveHeaderCallback callback) {
        Observable.just(wavFilePath)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        FileInputStream fileInputStream = new FileInputStream(new File(s));
                        BufferedInputStream dis = new BufferedInputStream(fileInputStream);
                        WaveHeader.Builder builder = new WaveHeader.Builder()
                                .setRiff(readString(dis, 4))
                                .setTotalLength(readInt(dis))
                                .setWave(readString(dis, 4))
                                .setFmt(readString(dis, 4))
                                .setTransition(readInt(dis))
                                .setType(readShort(dis))
                                .setChannelMask(readShort(dis))
                                .setSampleRate(readInt(dis))
                                .setRate(readInt(dis))
                                .setSampleLength(readShort(dis))
                                .setDeepness(readShort(dis));
                        int loopCount = 0;
                        while (loopCount < 100 && !(readString(dis, 2).equals("da") && readString(dis, 2).equals("ta"))) {
                            //再往后读100字节，忽略非标准格式数据，直到查找到“data”字符串
                            loopCount++;
                        }
                        if (loopCount < 100) {
                            builder.setData("data");
                            builder.setDataLength(readInt(dis));
                        }
                        WaveHeader header = builder.build();
                        dis.close();
                        fileInputStream.close();

                        if (!"riff".equalsIgnoreCase(header.getRiff())
                                || !"wave".equalsIgnoreCase(header.getWave())
                                || !"fmt ".equalsIgnoreCase(header.getFmt())
                                || !"data".equalsIgnoreCase(header.getData())) {
                            //无效header
                            if (callback != null) {
                                callback.onFailure("无效的wav文件");
                            }
                        } else {
                            if (callback != null) {
                                callback.onSuc(header);
                            }
                        }
                    }
                });
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
    }

    private static short readShort(InputStream inputStream) {
        byte[] bytes = new byte[2];
        try {
            inputStream.read(bytes);
            //{1, 0}
            return (short) ((bytes[0] & 0xff) | ((bytes[1] & 0xff) << 8));
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static int readInt(InputStream inputStream) {
        byte[] bytes = new byte[4];
        try {
            inputStream.read(bytes);
            return (int) ((bytes[0] & 0xff) | ((bytes[1] & 0xff) << 8) | ((bytes[2] & 0xff) << 16) | ((bytes[3] & 0xff) << 24));
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     *  byte 转二进制
     * @param bytes
     * @param radix 2：二进制
     * @return 二进制（补码）
     */
    public static String binary(byte bytes, int radix){
        byte[] bytes1 = new byte[1];
        bytes1[0] = bytes;
        return new BigInteger(1, bytes1).toString(radix);// 这里的1代表正数
    }

}

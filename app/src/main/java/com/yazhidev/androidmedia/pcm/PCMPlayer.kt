package com.yazhidev.androidmedia.pcm

import android.media.AudioFormat
import android.media.AudioTrack
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileInputStream

/**
 * Created by zengyazhi on 2018/10/19.
 *
 * 播放 PCM 裸数据
 */

class PCMPlayer {

    private var mAudioTrack: AudioTrack? = null
    private var mEnd = false

    init {
        createAudioTrack()
    }

    /**
     * channel 暂只支持单声道和双声道
     * deepness 暂只支持8位和16位
     */
    fun createAudioTrack(sampleRate: Int = 44100, channel: Int = 2, deepness: Int = 16) {
        mAudioTrack?.apply {
            destroy()
        }

        mEnd = false
        val format = AudioFormat.Builder()
                .setChannelMask(if (channel == 2) AudioFormat.CHANNEL_OUT_STEREO else AudioFormat.CHANNEL_IN_DEFAULT)
                .setSampleRate(sampleRate)
                .setEncoding(if (deepness == 16) AudioFormat.ENCODING_PCM_16BIT else AudioFormat.ENCODING_PCM_8BIT)
                .build()

        mAudioTrack = AudioTrack.Builder()
                .setAudioFormat(format)
                .build()
    }

    /**
     * 可跳过头部 from 个字节，用于播放 WAV 格式音频
     */
    fun playPcm(pcmFilePath: String, from: Int) {
        if (mAudioTrack == null) {
            throw IllegalStateException("AudioTrack not found, forget createAudioTrack ?")
        }
        mAudioTrack?.apply {
            play()
            val inputStream = FileInputStream(File(pcmFilePath))
            val buffer = ByteArray(bufferSizeInFrames)
            var len = 0
            Observable.just(inputStream)
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        it.use { input ->
                            var indexFrom = from
                            while ((input.read(buffer, 0, buffer.size).apply { len = this }) > 0 && !mEnd) {
                                write(buffer, indexFrom, len - indexFrom)
                                indexFrom = 0
                            }
                        }
                    }
        }
    }

    fun playPcm(pcmFilePath: String) {
        playPcm(pcmFilePath, 0)
    }

    fun destroy() {
        mAudioTrack?.apply {
            mEnd = true
            pause()
            flush()
            release()
        }
    }

}

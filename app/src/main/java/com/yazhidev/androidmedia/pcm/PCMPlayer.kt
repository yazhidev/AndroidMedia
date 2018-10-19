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

    fun createAudioTrack(sampleRate: Int = 44100) {
        mAudioTrack?.apply {
            destroy()
        }

        mEnd = false
        val format = AudioFormat.Builder()
                .setChannelMask(AudioFormat.CHANNEL_OUT_STEREO)
                .setSampleRate(sampleRate)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .build()

        mAudioTrack = AudioTrack.Builder()
                .setAudioFormat(format)
                .build()
    }

    fun playPcm(pcmFilePath: String) {
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
                            while ((input.read(buffer, 0, buffer.size).apply { len = this }) > 0 && !mEnd) {
                                write(buffer, 0, len)
                            }
                        }
                    }
        }
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

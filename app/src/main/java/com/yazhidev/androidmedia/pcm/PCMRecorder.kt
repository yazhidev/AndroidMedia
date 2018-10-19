package com.yazhidev.androidmedia.pcm

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.AudioRecord.ERROR_INVALID_OPERATION
import com.yazhidev.androidmedia.utils.LibToastUtils
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream

/**
 * Created by zengyazhi on 2018/10/19.
 */
class PCMRecorder(sampleRate: Int = 44100) {

    private var mRecording = false

    private var mRecorder: AudioRecord? = null

    init {
        val format = AudioFormat.Builder()
                .setChannelMask(AudioFormat.CHANNEL_OUT_STEREO)
                .setSampleRate(sampleRate)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .build()

        mRecorder = AudioRecord.Builder()
                .setAudioFormat(format)
                .build()
    }

    fun startRecord(path: String) {
        mRecorder?.apply {
            val outputStream = FileOutputStream(File(path))
            val buffer = ByteArray(bufferSizeInFrames)
            LibToastUtils.showToast("开始录制")
            startRecording()
            mRecording = true
            Observable.just(outputStream)
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        it.use { output ->
                            while (mRecording) {
                                val readSize = read(buffer, 0, buffer.size)
                                if(readSize != ERROR_INVALID_OPERATION) {
                                    output.write(buffer)
                                }
                            }
                            LibToastUtils.showToast("已结束录制，保存路径：${path}")
                            stop()
                            release()
                            mRecorder = null

                        }
                    }
        }
    }

    fun stopRecord() {
        mRecording = false
    }

}
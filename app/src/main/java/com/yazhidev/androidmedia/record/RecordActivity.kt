package com.yazhidev.androidmedia.record

import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import com.yazhidev.androidmedia.R
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_record.*
import java.io.File
import java.io.FileInputStream

class RecordActivity : AppCompatActivity() {

    private var mAudioTrack: AudioTrack? = null
    private var mEnd = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        playBtm.setOnClickListener {
            if (mAudioTrack == null) {
                mAudioTrack = createAudioTrack()
                        .apply {
                            play()
                            val pcmFilePath = "${Environment.getExternalStorageDirectory()}/16k.pcm"
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
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mAudioTrack?.apply {
            mEnd = true
            pause()
            flush()
            release()
        }
    }

    private fun createAudioTrack(): AudioTrack {
        val format = AudioFormat.Builder()
                .setChannelMask(AudioFormat.CHANNEL_OUT_STEREO)
                .setSampleRate(44100)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .build()

        return AudioTrack.Builder()
                .setAudioFormat(format)
                .build()
    }

}

package com.yazhidev.androidmedia.record

import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import com.yazhidev.androidmedia.R
import kotlinx.android.synthetic.main.activity_record.*
import java.io.File
import java.io.FileInputStream

class RecordActivity : AppCompatActivity() {

    private val mAudioTrack by lazy { createAudioTrack() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        playPcmBtn.setOnClickListener {
            mAudioTrack.play()

            val pcmFilePath = "${Environment.getExternalStorageDirectory()}/16k.pcm"
            val inputStream = FileInputStream(File(pcmFilePath))
            val buffer = ByteArray(mAudioTrack.bufferSizeInFrames)
            var len = 0

            inputStream.use { input ->
                while ((input.read(buffer, 0, buffer.size).apply { len = this }) > 0) {
                    mAudioTrack.write(buffer, 0, len)
                }
            }
        }
    }

    private fun createAudioTrack(): AudioTrack {
        val format = AudioFormat.Builder()
                .setChannelMask(AudioFormat.CHANNEL_OUT_STEREO)
                .setSampleRate(16000)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .build()

        return AudioTrack.Builder()
                .setAudioFormat(format)
                .build()
    }

}

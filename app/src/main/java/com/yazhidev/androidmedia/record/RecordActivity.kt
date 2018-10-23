package com.yazhidev.androidmedia.record

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.yazhidev.androidmedia.R
import com.yazhidev.androidmedia.pcm.PCMPlayer
import com.yazhidev.androidmedia.pcm.PCMRecorder
import com.yazhidev.androidmedia.utils.DecoderUtils
import com.yazhidev.androidmedia.utils.LibFileUtils
import kotlinx.android.synthetic.main.activity_record.*
import java.util.*


class RecordActivity : AppCompatActivity() {

    private val pcmPlayer by lazy { PCMPlayer() }
    private val pcmRecorder by lazy { PCMRecorder() }
    private var type = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        playBtn.setOnClickListener {
            //选择 PCM 文件
            type = 0
            LibFileUtils.pickFile(this)
        }

        recordBtn.setOnClickListener {
            //开始录制
            val path = "${Environment.getExternalStorageDirectory()}/${Date().time}.pcm"
            pcmRecorder.startRecord(path)
        }

        endRecordBtn.setOnClickListener {
            //结束录制
            pcmRecorder.stopRecord()
        }

        readWaveBtn.setOnClickListener {
            //选择文件
//            type = 1
//            LibFileUtils.pickFile(this)l;j ？     w`w
            val path = "${Environment.getExternalStorageDirectory()}/hurt.wav"
            DecoderUtils.readWavHeader(path)

        }

        val data = byteArrayOf(10, 15, 20, 44, 1, 3, 13, 59, 98, 12, 51, 5, 13, 15, 10)
        spectrumView.setData(data)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val path = LibFileUtils.handlePickFileResult(this, requestCode, resultCode, data)
        if(!TextUtils.isEmpty(path)) {
            if(type == 0) {
                pcmPlayer.createAudioTrack(44100)
                pcmPlayer.playPcm(path)
            } else if(type == 1) {
                DecoderUtils.readWavHeader(path)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pcmPlayer.destroy()
        pcmRecorder.stopRecord()
    }
}

package com.yazhidev.androidmedia.record

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.yazhidev.androidmedia.R
import com.yazhidev.androidmedia.pcm.PCMPlayer
import com.yazhidev.androidmedia.pcm.PCMRecorder
import com.yazhidev.androidmedia.utils.LibFileUtils
import kotlinx.android.synthetic.main.activity_record.*
import java.util.*


class RecordActivity : AppCompatActivity() {

    private val pcmPlayer by lazy { PCMPlayer() }
    private val pcmRecorder by lazy { PCMRecorder() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        playBtm.setOnClickListener {
            //选择 PCM 文件
            LibFileUtils.pickFile(this)
        }

        recordBtm.setOnClickListener {
            //开始录制
            val path = "${Environment.getExternalStorageDirectory()}/${Date().time}.pcm"
            pcmRecorder.startRecord(path)
        }

        endRecordBtm.setOnClickListener {
            //结束录制
            pcmRecorder.stopRecord()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val path = LibFileUtils.handlePickFileResult(this, requestCode, resultCode, data)
        if(!TextUtils.isEmpty(path)) {
            pcmPlayer.createAudioTrack(44100)
            pcmPlayer.playPcm(path)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pcmPlayer.destroy()
        pcmRecorder.stopRecord()
    }
}

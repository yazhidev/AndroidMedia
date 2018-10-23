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
import com.yazhidev.androidmedia.utils.LibToastUtils
import com.yazhidev.androidmedia.utils.ReadWaveHeaderCallback
import com.yazhidev.androidmedia.utils.bean.WaveHeader
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
            type = 1
            LibFileUtils.pickFile(this)
        }

        playWaveBtn.setOnClickListener {
            type = 2
            LibFileUtils.pickFile(this)
        }

        val data = byteArrayOf(10, 15, 20, 44, 1, 3, 13, 59, 98, 12, 51, 5, 13, 15, 10)
        spectrumView.setData(data)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val path = LibFileUtils.handlePickFileResult(this, requestCode, resultCode, data)
        if (!TextUtils.isEmpty(path)) {
            when (type) {
                0 -> {
                    pcmPlayer.createAudioTrack(44100)
                    pcmPlayer.playPcm(path)
                }
                1 -> {
                    DecoderUtils.readWavHeader(path, object : ReadWaveHeaderCallback {
                        override fun onSuc(header: WaveHeader) {
                            LibToastUtils.showToast("读取 wav 文件头成功！header长度：${header.totalLength + 8 - header.dataLength} 采样率：${header.sampleRate}")
                        }

                        override fun onFailure(msg: String) {
                            LibToastUtils.showToast(msg)
                        }
                    })
                }
                2 -> {
                    DecoderUtils.readWavHeader(path, object : ReadWaveHeaderCallback {
                        override fun onSuc(header: WaveHeader) {
                            pcmPlayer.createAudioTrack(header.sampleRate, header.channelMask.toInt(), header.deepness.toInt())
                            pcmPlayer.playPcm(path, header.totalLength + 8 - header.dataLength)
                        }

                        override fun onFailure(msg: String) {
                            LibToastUtils.showToast(msg)
                        }
                    })
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pcmPlayer.destroy()
        pcmRecorder.stopRecord()
    }
}


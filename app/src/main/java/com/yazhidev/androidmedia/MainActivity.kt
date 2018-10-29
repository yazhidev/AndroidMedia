package com.yazhidev.androidmedia

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yazhidev.androidmedia.opengles.OpenGLESActivity
import com.yazhidev.androidmedia.record.RecordActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()

        playPCM.setOnClickListener { startActivity(Intent(this, RecordActivity::class.java)) }

        gotoOpenGLES.setOnClickListener { startActivity(Intent(this, OpenGLESActivity::class.java))  }

    }

    private fun requestPermission() {
        RxPermissions(this)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe({ granted ->
                    if (granted) {
                    } else {
                    }
                })
    }


}

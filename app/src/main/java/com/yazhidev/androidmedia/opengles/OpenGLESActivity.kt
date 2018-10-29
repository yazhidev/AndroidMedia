package com.yazhidev.androidmedia.opengles

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class OpenGLESActivity : AppCompatActivity() {

    private lateinit var mGLView: GLSurfaceView
    private val mRender by lazy { MyGLRenderer() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGLView = GLSurfaceView(this)
                .apply {
                    setEGLContextClientVersion(3)
                    setRenderer(mRender)
                }
        setContentView(mGLView)
    }
}

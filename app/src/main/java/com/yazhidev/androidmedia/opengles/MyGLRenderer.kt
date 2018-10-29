package com.yazhidev.androidmedia.opengles

import android.opengl.GLES31
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by zengyazhi on 2018/10/27.
 */

class MyGLRenderer : GLSurfaceView.Renderer {

    private lateinit var mTriangle: Triangle

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        //设置背景颜色
        GLES31.glClearColor(0F, 0F, 0F, 1F)
        //初始化图形
        mTriangle = Triangle()
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT)
        mTriangle.draw()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES31.glViewport(0, 0, width, height)
    }

}

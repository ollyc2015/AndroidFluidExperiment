package com.android.ui.fluidSimulation

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.GLSurfaceView
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.egl.EGLDisplay
import javax.microedition.khronos.opengles.GL10


/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ /**
 *
 */
internal class FluidView : GLSurfaceView {

    constructor(context: Context?) : super(context) {
        init(false, 0, 0)
    }

    constructor(context: Context?, translucent: Boolean, depth: Int, stencil: Int) : super(context) {
        init(translucent, depth, stencil)
    }

    private fun init(translucent: Boolean, depth: Int, stencil: Int) {

        /* Setup the context factory for 2.0 rendering.
         * See ContextFactory class definition below
         */
        setEGLContextFactory(ContextFactory())


        /* Set the renderer responsible for frame rendering */setRenderer(Renderer())
    }

    private class ContextFactory : EGLContextFactory {
        override fun createContext(egl: EGL10, display: EGLDisplay, eglConfig: EGLConfig): EGLContext {
            Log.w(TAG, "creating OpenGL ES 2.0 context")
            checkEglError("Before eglCreateContext", egl)
            val attrib_list = intArrayOf(EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE)
            val context = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, attrib_list)
            checkEglError("After eglCreateContext", egl)
            return context
        }

        override fun destroyContext(egl: EGL10, display: EGLDisplay, context: EGLContext) {
            egl.eglDestroyContext(display, context)
        }

        companion object {
            private const val EGL_CONTEXT_CLIENT_VERSION = 0x3098
        }
    }

    private class Renderer : GLSurfaceView.Renderer {

        override fun onDrawFrame(gl: GL10) {
            FluidLib.step()
        }

        override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {

            FluidLib.init(width, height)

        }

        override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
            // Do nothing.


        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        val drawable = ResourcesCompat.getDrawable(resources, com.android.ui.fluidSimulation.R.drawable.loading_complete, null)


        when (event.actionMasked) {
            MotionEvent.ACTION_MOVE ->                 // Ensure that handling of the touch event is run on the GL thread
                // rather than Android UI thread. This ensures we can modify
                // rendering state without locking.  This event triggers a plane
                // fit.
            {
                var i = 0
                while (i < event.pointerCount) {
                    FluidLib.onTouchChange(event.getPointerId(i), event.getX(i), event.getY(i), event.getPressure(i), event.getSize(i))
                    i++
                }
            }
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                var i = 0
                while (i < event.pointerCount) {
                    FluidLib.onTouchDown(event.getPointerId(i), event.getX(i), event.getY(i), event.getPressure(i), event.getSize(i))
                    i++
                }
            }
            MotionEvent.ACTION_UP -> {
                var i = 0
                while (i < event.pointerCount) {
                    Log.i("ACTION_UP info ", event.toString())
                    FluidLib.onTouchUp(event.getPointerId(i), event.getX(i), event.getY(i), event.getPressure(i), event.getSize(i))
                    i++
                }
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val pointerIndex = event.actionIndex
                val pointerId = event.getPointerId(pointerIndex)
                var i = 0
                while (i < event.pointerCount) {
                    FluidLib.onTouchUp(pointerId, event.getX(i), event.getY(i), event.getPressure(i), event.getSize(i))
                    i++
                }
            }
        }
        return true
    }


    companion object {
        private const val TAG = "FluidView"
        private fun checkEglError(prompt: String, egl: EGL10) {
            var error: Int
            while (egl.eglGetError().also { error = it } != EGL10.EGL_SUCCESS) {
                Log.e(TAG, String.format("%s: EGL error: 0x%x", prompt, error))
            }
        }
    }
}
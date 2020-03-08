package com.android.ui.fluidSimulation

// Wrapper for native library

object FluidLib {
    /**
     * @param width the current view width
     * @param height the current view height
     */
    external fun init(width: Int, height: Int)
    external fun fluidDestroy()
    external fun step()

    // Respond to a touch event.
    external fun onTouchDown(id: Int, x: Float, y: Float, pressure: Float, size: Float)
    external fun onTouchUp(id: Int, x: Float, y: Float, pressure: Float, size: Float)
    external fun onTouchChange(id: Int, x: Float, y: Float, pressure: Float, size: Float) // public static native void releaseTouchEvent(int id);

    init {
        System.loadLibrary("gl_code")
    }
}
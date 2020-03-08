package com.android.ui.fluidSimulation


import android.animation.AnimatorSet
import android.animation.ObjectAnimator.ofFloat
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import com.android.common.TOUCH_DETECTED
import com.android.common.USER_CANCELLED_FLUID_ACTIVITY
import com.android.ui.loadSettings.LoadSettingsView
import java.lang.Math.cos
import java.lang.Math.sin


class FluidActivity : FragmentActivity() {

    private var fluidRing: ImageView? = null
    private var mView: FluidView? = null
    private var x: Double = 0.1
    private var y: Double = 0.1
    val delayTime = 100L
    var ringAnimationHandler: Handler = Handler()
    var hideRing = false

    val mediaPlayer = LoadSettingsView.getMediaPlayer()
    val settings = LoadSettingsView.getSettingsResponse()

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        mView = FluidView(application)
        setContentView(mView)
        init()
    }

    fun init() {

        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.loading_complete, null)

        fluidRing = ImageView(this)
        fluidRing?.foreground = drawable

        val layoutParams = RelativeLayout.LayoutParams(300, 300)
        time()
        layoutParams.leftMargin = x.toInt()
        layoutParams.topMargin = y.toInt()

        this.addContentView(fluidRing, layoutParams)
        // fluidRing?.visibility = View.GONE

        tutorialRingAnimation()

        mediaPlayer?.start()
        mediaPlayer?.isLooping = true


    }

    fun time() {

        val t: Double = System.currentTimeMillis().toDouble()
        val t_s = t / 1000

        x = sin(t_s) * 500 + 500 // x
        y = cos(t_s) * 500 + 1000 // y

    }


    @SuppressLint("ObjectAnimatorBinding")
    fun tutorialRingAnimation() {

        ringAnimationHandler.postDelayed(object : Runnable {

            override fun run() {

                if (!TOUCH_DETECTED) {

                    if (fluidRing?.visibility == View.INVISIBLE) {
                        fluidRing?.let { fadeInAndShowImage(it) }
                    }


                    time()
                    val animX = ofFloat(fluidRing, "x", x.toFloat() - 150)
                    val animY = ofFloat(fluidRing, "y", y.toFloat() - 150)
                    val animSetXY = AnimatorSet()
                    animSetXY.playTogether(animX, animY)
                    animSetXY.duration = 100
                    animSetXY.interpolator = LinearInterpolator()
                    animSetXY.start()

                    ringAnimationHandler.postDelayed(this, delayTime)

                } else {

                    if (fluidRing?.visibility == View.VISIBLE) {
                        fadeOutAndHideImage(fluidRing!!)
                    }

                    ringAnimationHandler.postDelayed(this, delayTime)
                }
            }

        }, delayTime)
    }


    private fun fadeOutAndHideImage(img: ImageView) {
        val fadeOut: Animation = AlphaAnimation(0f, 1f)
        fadeOut.interpolator = AccelerateInterpolator()
        fadeOut.duration = 1000
        fadeOut.setAnimationListener(object : AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                img.visibility = View.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationStart(animation: Animation) {}
        })
        img.startAnimation(fadeOut)
    }

    private fun fadeInAndShowImage(img: ImageView) {
        val fadeIn: Animation = AlphaAnimation(1f, 0f)
        fadeIn.interpolator = AccelerateInterpolator()
        fadeIn.duration = 1000
        fadeIn.setAnimationListener(object : AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                img.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationStart(animation: Animation) {}
        })
        img.startAnimation(fadeIn)
    }


    override fun onPause() {
        super.onPause()
        mView!!.onPause()
        mediaPlayer?.pause();
    }

    override fun onResume() {
        super.onResume()
        mView!!.onResume()
        mediaPlayer?.start();
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        USER_CANCELLED_FLUID_ACTIVITY = true
    }

}

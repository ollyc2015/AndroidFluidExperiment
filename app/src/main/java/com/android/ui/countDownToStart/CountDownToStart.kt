package com.android.ui.countDownToStart

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.android.common.BACKGROUND_ID
import com.android.common.USER_CANCELLED_FLUID_ACTIVITY
import com.android.ui.fluidSimulation.FluidActivity
import com.android.ui.fluidSimulation.R
import kotlinx.android.synthetic.main.activity_count_down_to_start.*
import java.util.concurrent.atomic.AtomicInteger


class CountDownToStart : Fragment() {

    private var handler = Handler()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_count_down_to_start, container, false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }



    override fun onStart() {
        super.onStart()
        if(USER_CANCELLED_FLUID_ACTIVITY){
            fragmentManager?.popBackStack();
        }
    }


    private fun init() {

        val backgroundID = arguments?.getInt(BACKGROUND_ID, 0)!!
        countDownBackground.background = ResourcesCompat.getDrawable(resources, backgroundID, null)

        countDownTimer()

    }

    private fun countDownTimer() {

        //val fadeOut = AlphaAnimation(1.0f, 0.0f)
        val fadeIn = AlphaAnimation(0.0f, 1.0f)

        countDownTextView.text = "3"
        countDownTextView.startAnimation(fadeIn)
        fadeIn.duration = 1500
        fadeIn.fillAfter = true

        handler = Handler()
        val n = AtomicInteger(2)
        val counter: Runnable = object : Runnable {
            override fun run() {

                if (n.get() != 0) {
                    countDownTextView.text = n.get().toString()
                }

                if (n.getAndDecrement() >= 1) {

                    countDownTextView.startAnimation(fadeIn)
                    fadeIn.duration = 1500
                    fadeIn.fillAfter = true

                    handler.postDelayed(this, 1500)

                } else {
                    countDownTextView.visibility = View.GONE
                    // start the fluid sim
                    goToNextScreen()
                }
            }
        }
        handler.postDelayed(counter, 1500)

    }

    override fun onDetach() {
        super.onDetach()
        handler.removeCallbacksAndMessages(null)
    }

    private fun goToNextScreen() {

        val intent = Intent()
        activity?.let { intent.setClass(it, FluidActivity::class.java) }
        activity?.startActivity(intent)
    }
}

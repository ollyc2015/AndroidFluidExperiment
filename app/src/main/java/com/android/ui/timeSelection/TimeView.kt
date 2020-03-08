package com.android.ui.timeSelection

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.common.*
import com.android.ui.fluidSimulation.R
import com.android.ui.loadSettings.LoadSettingsView
import kotlinx.android.synthetic.main.moods_time.*


class TimeView : Fragment() {

    var timeSelected: String? = "2"
    var timeOptions = MOOD_TIME_ARRAY
    var chosenWord: String? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.moods_time, container, false)
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


    fun init() {

        val backgroundID = arguments?.getInt(BACKGROUND_ID, 0)!!
        chosenWord = arguments?.getString(CHOSEN_WORD)!!

        background_image1.setBackgroundResource(backgroundID)

        btnTime?.text = "\u2192".toCharArray()[0].toString()
        btnTime?.textSize = 50f
        btnTime?.setPadding(0, 0, 0, 50)

        timePicker?.minValue = 1
        timePicker?.maxValue = 3
        timePicker?.displayedValues = timeOptions
        timePicker?.typeface = Typeface.DEFAULT_BOLD

        timePicker?.setOnValueChangedListener { _, oldVal, newVal ->

            timeSelected = timeOptions[newVal - 1]

        }

        txtChosenWord?.text = "$chosenWord"
        btnTime?.setOnClickListener {

            goToNextScreen(backgroundID)
        }
    }

    fun goToNextScreen(backgroundID: Int) {

        val bundle = Bundle()
        bundle.putInt(BACKGROUND_ID, backgroundID)
        bundle.putString(CHOSEN_WORD, chosenWord)
        bundle.putString(TIME_SELECTED, timeSelected)

        val fragobj = LoadSettingsView()
        fragobj.arguments = bundle


        val fr = fragmentManager?.beginTransaction()
        fr?.replace(R.id.container, fragobj)
        fr?.addToBackStack(null)
        fr?.commit()


    }
}

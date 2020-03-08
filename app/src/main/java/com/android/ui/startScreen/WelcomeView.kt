package com.android.ui.startScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.android.ui.fluidSimulation.R
import com.android.ui.moodSelection.PreMoodView
import kotlinx.android.synthetic.main.moods_intro.*


//Start screen
class WelcomeView : Fragment() {

    companion object {
        fun newInstance() = WelcomeView()
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.moods_intro, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

     fun init() {
        btnImmerse?.setOnClickListener { goToNextScreen() }
    }

     fun goToNextScreen() {

         val fr = fragmentManager?.beginTransaction()
         fr?.replace(R.id.container, PreMoodView.newInstance())
         fr?.addToBackStack(null)
         fr?.commit()

    }
}
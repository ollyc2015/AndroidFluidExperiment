package com.android.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.android.ui.fluidSimulation.R
import com.android.ui.startScreen.WelcomeView


class MainActivity : FragmentActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_activity)
        if (savedInstanceState == null) {
               supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    WelcomeView.newInstance()
                )
                .commitNow()
        }
    }
}

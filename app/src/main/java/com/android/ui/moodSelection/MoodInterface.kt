package com.android.ui.moodSelection

import android.graphics.drawable.Drawable

interface MoodInterface {

    interface View {
        fun init()
        fun goToNextScreen(word: String?)
    }

    interface Presenter {
        fun attachView(view: View?)
        fun backgroundSelector(background: String?): Drawable?
    }
}
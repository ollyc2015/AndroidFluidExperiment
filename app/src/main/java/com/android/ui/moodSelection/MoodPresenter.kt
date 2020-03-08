package com.android.ui.moodSelection

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.android.ui.fluidSimulation.R

class MoodPresenter : MoodInterface.Presenter {

    private var view: MoodInterface.View? = null

    override fun attachView(view: MoodInterface.View?) {
        this.view = view
    }

    override fun backgroundSelector(chosenWord: String?): Drawable? {
        if (chosenWord == "ambitious_two_mins") {
            return ResourcesCompat.getDrawable(Resources.getSystem(), R.drawable.ambitious, null)!!
        } else if (chosenWord == "esteemed") {
            return ResourcesCompat.getDrawable(Resources.getSystem(), R.drawable.esteemed, null)!!
        }
        return ResourcesCompat.getDrawable(Resources.getSystem(), R.drawable.ambitious, null)!!
    }
}
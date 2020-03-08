package com.android.ui.moodSelection

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.fragment.app.Fragment
import com.android.common.*
import com.android.ui.fluidSimulation.R
import com.android.ui.timeSelection.TimeView
import kotlinx.android.synthetic.main.moods_words.*


class MoodView : Fragment(), MoodInterface.View {


    private var words = MOOD_WORDS_ARRAY
    private var resId = R.drawable.ambitious
    private var arrayPostion = 0
    private var moodPresenter = MoodPresenter()


    companion object {
        fun newInstance() = MoodView()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.moods_words, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun onStart() {
        super.onStart()
        USER_CANCELLED_FLUID_ACTIVITY = false
    }

    override fun init() {

        moodPresenter.attachView(this)

        btnNext?.text = "\u2192".toCharArray()[0].toString()
        btnNext?.textSize = 50f
        btnNext?.setPadding(0, 0, 0, 50)

        txtWords?.text = "1/${words.size}"

        textPicker?.minValue = 1
        textPicker?.maxValue = words.size
        textPicker?.displayedValues = words
        textPicker?.typeface = Typeface.DEFAULT_BOLD

        background_image.setBackgroundResource(R.drawable.ambitious)

        animateBackground()
        moodSelectionListener()
        nextScreenButton()


    }

    private fun moodSelectionListener(){

        // OnValueChangeListener
        textPicker?.setOnValueChangedListener { _, oldVal, newVal ->

            arrayPostion = newVal

            //Bodge job of getting the resource
            resId = resources.getIdentifier(words[newVal-1].toLowerCase().replace(" ", "_"), "drawable", activity?.packageName)
            txtWords?.text = "${arrayPostion}/${words.size}"

            background_image?.setBackgroundResource(resId)

        }
    }

    private fun nextScreenButton(){

        btnNext?.setOnClickListener {

            if(arrayPostion == 0){

                val chosenWord = words[arrayPostion]
                goToNextScreen(chosenWord)

            }else {
                val chosenWord = words[arrayPostion - 1]
                goToNextScreen(chosenWord)
            }
        }

    }


    private fun animateBackground() {
        val animation = TranslateAnimation(-10.0f, 10.0f,
                0.0f, 10.0f) //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.duration = 5000 // animation duration
        animation.repeatCount = -1 // animation repeat count
        animation.repeatMode = 2 // repeat animation (left to right, right to left )
        background_image?.startAnimation(animation) // start animation
    }

    override fun goToNextScreen(word: String?) {

        val bundle = Bundle()
        bundle.putInt(BACKGROUND_ID, resId)
        bundle.putString(CHOSEN_WORD, word)

        val fragobj = TimeView()
        fragobj.arguments = bundle

        val fr = fragmentManager?.beginTransaction()
        fr?.replace(R.id.container, fragobj)
        fr?.addToBackStack(null)
        fr?.commit()
    }
}
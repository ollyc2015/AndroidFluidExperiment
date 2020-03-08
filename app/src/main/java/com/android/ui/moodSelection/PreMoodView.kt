package com.android.ui.moodSelection


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.android.ui.fluidSimulation.R
import kotlinx.android.synthetic.main.moods_feel.*


class PreMoodView : Fragment(), MoodInterface.View {

    var word: String? = null
    var moodPresenter = MoodPresenter()

    companion object {
        fun newInstance() = PreMoodView()
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.moods_feel, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        init()
    }


    override fun init() {

        btnFeel?.text = "\u2192".toCharArray()[0].toString()
        btnFeel.textSize = 50f
        btnFeel.setPadding(0, 0, 0, 50)

        videoView?.setOnPreparedListener { mp -> mp.isLooping = true }
        val uri = Uri.parse("android.resource://" + activity?.packageName + "/" + R.raw.moods_splash_en)
        videoView?.setVideoURI(uri)
        videoView?.start() //need to make transition seamless.

        //vv.setZOrderOnTop(true);
        btnFeel?.setOnClickListener { goToNextScreen(word.toString()) }
        moodPresenter.attachView(this)
    }

    override fun goToNextScreen(word: String?) {

        val fr: FragmentTransaction = fragmentManager?.beginTransaction()!!
        fr.replace(R.id.container, MoodView())
        fr.addToBackStack(null)
        fr.commit()

    }

}
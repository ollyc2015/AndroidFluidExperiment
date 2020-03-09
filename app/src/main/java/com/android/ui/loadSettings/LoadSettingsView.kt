package com.android.ui.loadSettings

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.common.*
import com.android.ui.fluidSimulation.R
import com.android.ui.countDownToStart.CountDownToStart
import com.lush.retrofit.soa.service.settings.SettingsResponse
import kotlinx.android.synthetic.main.progress_view.*
import java.util.*

class LoadSettingsView : Fragment() {

    var backgroundID = 0
    var chosenWord: String? = null
    var timeSelected: String? = null
    var mediaPlayer: MediaPlayer? = null


    private lateinit var viewModel: LoadSettingsPresenter


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.progress_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoadSettingsPresenter::class.java)
        init()
    }

    override fun onStart() {
        super.onStart()
        if (USER_CANCELLED_FLUID_ACTIVITY) {
            fragmentManager?.popBackStack();
        }
    }

    private fun init() {

        Log.d("olly", "i am called")

        backgroundID = arguments?.getInt(BACKGROUND_ID, 0)!!
        chosenWord = arguments?.getString(CHOSEN_WORD)!!
        timeSelected = arguments?.getString(TIME_SELECTED)!!

        loading_status.background = ResourcesCompat.getDrawable(resources, backgroundID, null)

        btnStartFluid.text = "\u2192".toCharArray()[0].toString()
        btnStartFluid?.textSize = 50f
        btnStartFluid?.setPadding(0, 0, 0, 50)
        btnStartFluid.setOnClickListener { goToNextScreen() }

        listenForLoadingStatus()
        listenForMusicLoadingComplete()

        val moodRequested = chosenWord?.toLowerCase(Locale.ROOT)?.replace(" ", "")
        viewModel.loadingBar("$moodRequested.json")

    }


    private fun listenForLoadingStatus() {

        viewModel.getSettingsResponse()?.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                saveSettingsResponse(it)

                var modifiedChosenWord = chosenWord?.replace(" ", "")
                //This is needed due to a spelling mistake in the URL
                if(modifiedChosenWord == ENLIGHTENED){
                    modifiedChosenWord = "Enlightend"
                }

                for (item in MOOD_WORDS_ARRAY)

                    if (chosenWord == item && timeSelected == TIME_2_MINS) {

                        viewModel.requestMusic(modifiedChosenWord + "_2.mp3", mediaPlayer)

                    } else if (chosenWord == item && timeSelected == TIME_5_MINS) {

                        viewModel.requestMusic(modifiedChosenWord + "_5.mp3", mediaPlayer)

                    } else if (chosenWord == item && timeSelected == TIME_8_MINS) {

                        viewModel.requestMusic(modifiedChosenWord + "_8.mp3", mediaPlayer)
                    }

            }
        })
    }

    private fun listenForMusicLoadingComplete() {

        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.loading_complete, null)

        viewModel.getMediaPlayer()?.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                progressBar?.indeterminateDrawable = null
                progressBar?.background = drawable
                btnStartFluid.visibility = View.VISIBLE

                setMediaPlayer(it)

            }
        })
    }


    private fun goToNextScreen() {

        val bundle = Bundle()
        bundle.putInt(BACKGROUND_ID, backgroundID)

        val fragobj = CountDownToStart()
        fragobj.arguments = bundle

        val fr = fragmentManager?.beginTransaction()
        fr?.add(R.id.container, fragobj) //add to backstack so that network call isn't made when user presses the back button (call it when they return to this activity)
        fr?.addToBackStack(null) // adds fragment to backstack
        fr?.commit()
    }

    companion object {

        private var mySettingsResponse: SettingsResponse? = null
        private var myMediaPlayer: MediaPlayer? = null

        fun saveSettingsResponse(settingsResponse: SettingsResponse?) {

            mySettingsResponse = settingsResponse
        }


        fun getSettingsResponse(): SettingsResponse? {

            return mySettingsResponse
        }


        fun setMediaPlayer(mediaPlayer: MediaPlayer) {

            myMediaPlayer = mediaPlayer

        }

        fun getMediaPlayer(): MediaPlayer? {

            return myMediaPlayer

        }
    }
}
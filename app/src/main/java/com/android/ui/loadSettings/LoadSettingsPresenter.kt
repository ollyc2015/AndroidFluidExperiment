package com.android.ui.loadSettings

import android.annotation.SuppressLint
import android.app.Application
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.dependency_inject.SampleApplication
import com.lush.retrofit.soa.service.settings.SettingsResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoadSettingsPresenter(application: Application) : AndroidViewModel(application) {

    private var settingsRepo: SettingsRepo = SettingsRepo()
    private var response: MutableLiveData<SettingsResponse>? = null
    private var liveDataMediaPlayer: MutableLiveData<MediaPlayer>? = null
    private var myMediaPlayer: MediaPlayer? = null


    fun getSettingsResponse(): MutableLiveData<SettingsResponse>? {
        if (response == null) {
            response = MutableLiveData()

        }
        return response
    }

    fun getMediaPlayer(): MutableLiveData<MediaPlayer>? {
        if (liveDataMediaPlayer == null) {
            liveDataMediaPlayer = MutableLiveData()

        }
        return liveDataMediaPlayer
    }


    @SuppressLint("CheckResult")
    fun loadingBar(moodRequested: String) {


        settingsRepo.loadMusicRXJava(moodRequested)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->  response?.value = result},
                        { error -> println("There was an error: " + error.localizedMessage) })

    }


    @Throws(Exception::class)
    fun requestMusic(trackSelected: String, mediaPlayer: MediaPlayer?) {

        myMediaPlayer = mediaPlayer

        val url = "https://storage.googleapis.com/lush-labs/Moods/Music/$trackSelected"

        if (myMediaPlayer == null) {

            myMediaPlayer = MediaPlayer().apply {

                setDataSource(url)
                prepareAsync()

            }
        }
        myMediaPlayer?.setOnCompletionListener { killMediaPlayer() }
        liveDataMediaPlayer?.value = myMediaPlayer
    }

    private fun killMediaPlayer() {
        if (myMediaPlayer != null) {
            try {
                myMediaPlayer?.reset()
                myMediaPlayer?.release()
                myMediaPlayer = null
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}



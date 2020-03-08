package com.android.ui.loadSettings

import com.lush.retrofit.soa.service.settings.SettingsApi
import com.lush.retrofit.soa.service.settings.SettingsResponse
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class SettingsRepo : KoinComponent {

    private val settingsApi: SettingsApi by inject()

    //Below is the call to handle a RX response
    internal fun loadMusicRXJava(moodRequested: String): Single<SettingsResponse> {

        return Single.fromCallable { settingsApi.getSettings(moodRequested).execute().body() }
    }

}

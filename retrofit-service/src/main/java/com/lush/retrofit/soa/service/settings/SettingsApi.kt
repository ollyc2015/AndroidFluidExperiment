package com.lush.retrofit.soa.service.settings


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface SettingsApi {


    @GET("lush-labs/Moods/Settings/{mood_requested}")
    fun getSettings(@Path("mood_requested") moodRequested: String): Call<SettingsResponse>


}
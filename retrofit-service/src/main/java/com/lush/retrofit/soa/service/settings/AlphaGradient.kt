package com.lush.retrofit.soa.service.settings


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlphaGradient(
    @Json(name = "angle")
    val angle: Int,
    @Json(name = "stops")
    val stops: List<Stop>
)
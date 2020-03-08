package com.lush.retrofit.soa.service.settings


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Stop(
    @Json(name = "color")
    val color: List<Double>,
    @Json(name = "p")
    val p: Double
)
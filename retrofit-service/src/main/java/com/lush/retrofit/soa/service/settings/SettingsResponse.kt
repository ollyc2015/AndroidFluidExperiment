package com.lush.retrofit.soa.service.settings


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SettingsResponse(
    @Json(name = "alphaGradient")
    val alphaGradient: AlphaGradient,
    @Json(name = "backgroundMultiplier")
    val backgroundMultiplier: Int,
    @Json(name = "backgroundPalette")
    val backgroundPalette: BackgroundPalette,
    @Json(name = "dragCoefficient")
    val dragCoefficient: Int,
    @Json(name = "dragSpeed")
    val dragSpeed: Double,
    @Json(name = "dyeGradient")
    val dyeGradient: DyeGradient,
    @Json(name = "fluidPhysicsScale")
    val fluidPhysicsScale: Double,
    @Json(name = "gamma")
    val gamma: Double,
    @Json(name = "motionDecayFactor")
    val motionDecayFactor: Double,
    @Json(name = "opticalFlowBlurKernel")
    val opticalFlowBlurKernel: Double,
    @Json(name = "opticalFlowExponent")
    val opticalFlowExponent: Double,
    @Json(name = "opticalFlowGamma")
    val opticalFlowGamma: Double,
    @Json(name = "opticalFlowScale")
    val opticalFlowScale: Double,
    @Json(name = "opticalFlowTemporalSmoothing")
    val opticalFlowTemporalSmoothing: Double,
    @Json(name = "paused")
    val paused: Boolean,
    @Json(name = "periodicBoundary")
    val periodicBoundary: Boolean,
    @Json(name = "surfaceDecayFactor")
    val surfaceDecayFactor: Double,
    @Json(name = "timestepMultiplier")
    val timestepMultiplier: Double,
    @Json(name = "velocityGradient")
    val velocityGradient: VelocityGradient,
    @Json(name = "version")
    val version: Int
)
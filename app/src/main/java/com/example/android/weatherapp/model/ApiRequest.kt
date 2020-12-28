package com.example.android.weatherapp.model

import com.google.gson.annotations.SerializedName

data class TemperatureHolder (@SerializedName ("temp") val temperature: Float)

data class TemperatureRequest (@SerializedName("main") val mainTemperature: TemperatureHolder)
package com.example.android.weatherapp

import com.example.android.weatherapp.model.City
import com.example.android.weatherapp.model.TemperatureRequest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/data/2.5/weather")

    //?q={city name}&appid={API key}"

    //my KEY is: db94bca58864385799d4f629f32a6bf1

    fun fetchTemperatureByCityName(@Query("q") cityName:String,
                        @Query("appid") apiKey:String="db94bca58864385799d4f629f32a6bf1")
            : Call<TemperatureRequest>

}
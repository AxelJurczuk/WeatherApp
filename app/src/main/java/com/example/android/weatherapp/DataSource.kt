package com.example.android.weatherapp

import com.example.android.weatherapp.model.City

class DataSource {

    fun loadCityList():MutableList<City>{
        val cityList = mutableListOf<City>()
        cityList.add(City ("Buenos Aires"))
        cityList.add(City ("Londres"))
        cityList.add(City ("Barcelona"))
        cityList.add(City ("Roma"))
        cityList.add(City ("Berlin"))
        cityList.add(City ("Sydney"))
        cityList.add(City ("Paris"))
        cityList.add(City ("Brasilia"))
        cityList.add(City ("Hong Kong"))
        cityList.add(City ("Budapest"))
        cityList.add(City ("Montevideo"))

        return cityList
    }
}
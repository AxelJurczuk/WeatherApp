package com.example.android.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.weatherapp.databinding.ActivityAddCityBinding

import com.example.android.weatherapp.databinding.ActivityMainBinding

class AddCityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val myFragment = AddCityFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.frame_add_city_layout, myFragment)
            .commit()

    }
}
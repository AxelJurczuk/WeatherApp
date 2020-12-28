package com.example.android.weatherapp

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.weatherapp.databinding.FragmentAddCityBinding
import com.example.android.weatherapp.model.City
import com.google.gson.Gson


class AddCityFragment : Fragment() {

    private lateinit var binding: FragmentAddCityBinding

    private val key = "new city"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddCityBinding.inflate(layoutInflater)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener{

            val newCity= binding.addCityEditText.text.toString()

            val resultIntent = Intent (requireContext(),AddCityActivity::class.java)
            resultIntent.putExtra(key, newCity)
            activity?.setResult(RESULT_OK, resultIntent)
            activity?.finish()
        }
    }

}
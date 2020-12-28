package com.example.android.weatherapp

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.android.weatherapp.databinding.FragmentWeatherListBinding
import com.example.android.weatherapp.model.City
import com.example.android.weatherapp.model.TemperatureRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class WeatherListFragment : Fragment(), ItemAdapter.OnItemClick {

    private lateinit var binding: FragmentWeatherListBinding
    private lateinit var adapter: ItemAdapter

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ApiService::class.java)

    private val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
    private val editor = sharedPref?.edit()

    private val citiesList = mutableListOf<City>()
    private val gson = Gson()

    private val key = "saved city"

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding= FragmentWeatherListBinding.inflate(layoutInflater)
        return binding.root

    }
    //enable options menu in this fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.new_city -> {
                val intent = Intent(requireContext(), AddCityActivity::class.java)
                startActivityForResult(intent, 1111)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode==11111) && (resultCode==RESULT_OK)) {
            /*todas estas lineas las deje escritas porque no llegue a hacer la parte del SharedPrefs
            Recupero el String del nombre de la ciudad del otro fragment y lo guardo, pero despues
            deberia guardarlo en una lista y a su vez esa lista guardarla con Shared. Y la lista que le
            paso al adapter deberia ser la que recupero del Shared...
            Fui inverstigando y probando cosas y no lo logro
             */
            val newCity = data?.getStringExtra("new city")
            editor?.putString(key, newCity)
            editor?.apply()

            citiesList.add(City(newCity!!))
            /*
            val json = gson.toJson(citiesList)
            editor?.putString(key, json)
            editor?.apply()
             */
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        api.fetchTemperatureByCityName("London").enqueue(object : Callback<TemperatureRequest> {
            override fun onResponse(call: Call<TemperatureRequest>, response: Response<TemperatureRequest>) {
                Log.d("axel", "${response.body()}")
            }
            override fun onFailure(call: Call<TemperatureRequest>, t: Throwable) {
                Log.e("axel", "exception", t)
            }
        })

        val cityList = DataSource().loadCityList()
        adapter = ItemAdapter(requireContext(), cityList, this)
        adapter.notifyDataSetChanged()

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }
    override fun onItemClickListener(position: Int) {
        api.fetchTemperatureByCityName(adapter.cityList[position].cityName).enqueue(object : Callback<TemperatureRequest> {

            override fun onResponse(call: Call<TemperatureRequest>, response: Response<TemperatureRequest>) {

                val tempKelvin = response.body()?.mainTemperature?.temperature
                val tempCelsius = tempKelvin?.minus(273.15)?.toInt()

                Toast.makeText(requireContext(),
                        "Temperature is: $tempCelsius°C",
                        Toast.LENGTH_SHORT)
                        .show()
            }

            override fun onFailure(call: Call<TemperatureRequest>, t: Throwable) {
                Log.e("axel", "exception", t)
            }
        })
    }
}

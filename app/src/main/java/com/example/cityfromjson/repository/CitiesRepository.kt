package com.example.cityfromjson.repository

import android.content.Context
import com.example.cityfromjson.models.CityModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CitiesRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun loadCities(): List<CityModel> {
        val inputStream = context.assets.open("cities.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        return Gson().fromJson(json, object : TypeToken<List<CityModel>>() {}.type)
    }
}

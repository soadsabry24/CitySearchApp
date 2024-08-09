package com.example.cityfromjson.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cityfromjson.models.CityModel
import com.example.cityfromjson.repository.CitiesRepository


import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(private val repository: CitiesRepository) : ViewModel() {

    private val _filteredCities = MutableLiveData<List<CityModel>>()
    val filteredCities: LiveData<List<CityModel>> = _filteredCities

    init {
        _filteredCities.value = repository.loadCities().sortedBy { it.name }
    }

    fun filterCities(prefix: String) {
        val cities = repository.loadCities()
        _filteredCities.value = cities.filter { it.name.startsWith(prefix, ignoreCase = true) }.sortedBy { it.name }
    }
}

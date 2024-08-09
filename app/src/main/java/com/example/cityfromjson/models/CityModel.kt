package com.example.cityfromjson.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



data class CityModel(
    val country: String,
    val name: String,
    val id: Long,
    val coord: Coord
)

data class Coord(
    val lon: Double,
    val lat: Double
)

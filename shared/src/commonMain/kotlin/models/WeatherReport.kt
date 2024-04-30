package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val lat: Double,
    val lon: Double,
    val name: String,
    val region: String,
    @SerialName("tz_id")
    val timezone: String,
    val country: String,
)

@Serializable
data class WeatherCondition(
    val icon: String,
    @SerialName("text")
    val text: String,
)

@Serializable
data class Weather(
    @SerialName("temp_f")
    val tempF: Double,
    @SerialName("temp_c")
    val tempC: Double,
    @SerialName("wind_kph")
    val windKPH: Double,
    @SerialName("wind_mph")
    val windMPH: Double,
    val humidity: Double,
    val condition: WeatherCondition,
)

@Serializable
data class WeatherReport(
    val location: Location,
    val current: Weather,
)
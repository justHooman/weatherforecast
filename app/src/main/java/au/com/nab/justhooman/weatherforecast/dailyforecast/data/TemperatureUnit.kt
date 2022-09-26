package au.com.nab.justhooman.weatherforecast.dailyforecast.data

enum class TemperatureUnit {
    Kelvin,
    Celsius,
    Fahrenheit
}

fun TemperatureUnit.toQuery() : String {
    return when (this) {
        TemperatureUnit.Celsius -> "metric"
        TemperatureUnit.Fahrenheit -> "imperial"
        TemperatureUnit.Kelvin -> "standard"
    }
}

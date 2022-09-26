package au.com.nab.justhooman.weatherforecast.dailyforecast.ui.adapter

import androidx.annotation.VisibleForTesting
import au.com.nab.justhooman.weatherforecast.BuildConfig
import au.com.nab.justhooman.weatherforecast.dailyforecast.data.Config
import au.com.nab.justhooman.weatherforecast.dailyforecast.data.SearchInput
import au.com.nab.justhooman.weatherforecast.dailyforecast.data.TemperatureUnit


fun String.toSearchInput(): SearchInput {
    val queries = this.split(",").map { it.trim() }
    val query = queries.firstOrNull().orEmpty()
    val count = parseDayCount(queries)
    val unit = parseTemperatureUnit(queries)
    return SearchInput(
        appId =  BuildConfig.OPEN_WEATHER_MAP_APPID,
        query = query,
        count = count,
        units = unit
    )
}

@VisibleForTesting
internal fun parseDayCount(queries: List<String>): Int {
    val countPrefix = "day="
    return queries.firstOrNull { it.startsWith(countPrefix) }
        ?.substring(countPrefix.length)?.toIntOrNull() ?: Config.queryDayDefault
}

@VisibleForTesting
internal fun parseTemperatureUnit(queries: List<String>): TemperatureUnit {
    val unitPrefix = "unit="
    return queries.firstOrNull { it.startsWith(unitPrefix) }
        ?.substring(unitPrefix.length).let { unit ->
            when (unit) {
                "c" -> TemperatureUnit.Celsius
                "f" -> TemperatureUnit.Fahrenheit
                else -> TemperatureUnit.Kelvin
            }
        }
}
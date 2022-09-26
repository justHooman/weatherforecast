package au.com.nab.justhooman.weatherforecast.dailyforecast.data

data class SearchInput(
    val query: String,
    val count: Int = Config.queryDayDefault,
    val appId: String,
    val units: TemperatureUnit = TemperatureUnit.Kelvin
)

fun SearchInput.toQueryMap(): Map<String, String> {
    return mapOf(
        "q" to query,
        "cnt" to count.coerceAtLeast(Config.queryDayMin).coerceAtMost(Config.queryDayMax).toString(),
        "appid" to appId,
        "units" to units.toQuery()
    )
}
package au.com.nab.justhooman.weatherforecast.dailyforecast.data

import androidx.core.util.lruCache
import au.com.nab.justhooman.weatherforecast.dailyforecast.api.DailyForecastService
import au.com.nab.justhooman.weatherforecast.dailyforecast.api.dto.SearchResponse
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

enum class TemperatureUnit {
    Kelvin,
    Celsius,
    Fahrenheit
}

data class SearchInput(
    val query: String,
    val count: Int = 7,
    val appId: String,
    val units: TemperatureUnit = TemperatureUnit.Kelvin
)

interface DailyForecastRepo {
    suspend fun search(searchQuery: SearchInput): Result<SearchResponse>
}

class DailyForecastRepoImpl @Inject constructor(
    @Named(RepoType.NETWORK) private val networkRepoProvider: Provider<DailyForecastRepo>
) : DailyForecastRepo {

    private val cachedResults = lruCache<String, SearchResponse>(Config.cachedQueriesSize)

    override suspend fun search(searchQuery: SearchInput): Result<SearchResponse> {
        val cacheKey = searchQuery.toCacheKey()
        return cachedResults[cacheKey]?.let { Result.success(it) }
            ?: networkRepoProvider.get().search(searchQuery).also { networkResult ->
                if (networkResult.isSuccess) {
                    networkResult.getOrNull()?.let { searchResponse ->
                        cachedResults.put(cacheKey, searchResponse)
                    }
                }
            }
    }

    private fun SearchInput.toCacheKey(): String {
        return "$query,$count,$units"
    }
}

class DailyForecastRepoNetworkImpl @Inject constructor(
    private val serviceProvider: Provider<DailyForecastService>
) : DailyForecastRepo {
    override suspend fun search(searchQuery: SearchInput): Result<SearchResponse> {
        return kotlin.runCatching {
            serviceProvider.get().search(searchQuery.toQueryMap())
        }
    }

    private fun SearchInput.toQueryMap(): Map<String, String> {
        return mapOf(
            "q" to query,
            "cnt" to count.coerceAtLeast(Config.queryDayMin).coerceAtMost(Config.queryDayMax).toString(),
            "appid" to appId,
            "units" to units.toQuery()
        )
    }

    private fun TemperatureUnit.toQuery() : String {
        return when (this) {
            TemperatureUnit.Celsius -> "metric"
            TemperatureUnit.Fahrenheit -> "imperial"
            TemperatureUnit.Kelvin -> "standard"
        }
    }
}
package au.com.nab.justhooman.weatherforecast.dailyforecast.data

import androidx.collection.lruCache
import au.com.nab.justhooman.weatherforecast.dailyforecast.api.DailyForecastService
import au.com.nab.justhooman.weatherforecast.dailyforecast.api.dto.SearchResponse
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

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
}
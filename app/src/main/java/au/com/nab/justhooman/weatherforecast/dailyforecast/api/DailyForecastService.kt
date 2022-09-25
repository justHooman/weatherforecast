package au.com.nab.justhooman.weatherforecast.dailyforecast.api

import au.com.nab.justhooman.weatherforecast.dailyforecast.api.dto.SearchResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface DailyForecastService {
    companion object {
        object ApiPath {
            const val SEARCH = "data/2.5/forecast/daily"
        }
    }

    @GET(ApiPath.SEARCH)
    suspend fun search(@QueryMap(encoded = true) queries: Map<String, String>): SearchResponse
}
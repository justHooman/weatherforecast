package au.com.nab.justhooman.weatherforecast.dailyforecast.api

import au.com.nab.justhooman.weatherforecast.dailyforecast.dto.SearchResponse

interface DailyForecastService {
    companion object {
        object API_PATH {
            const val SEARCH = "data/2.5/forecast/daily"
        }
    }

    suspend fun search(): SearchResponse
}
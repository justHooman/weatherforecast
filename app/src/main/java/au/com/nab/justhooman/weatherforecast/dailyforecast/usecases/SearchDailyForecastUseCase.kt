package au.com.nab.justhooman.weatherforecast.dailyforecast.usecases

import au.com.nab.justhooman.weatherforecast.dailyforecast.api.dto.SearchResponse
import au.com.nab.justhooman.weatherforecast.dailyforecast.data.DailyForecastRepo
import au.com.nab.justhooman.weatherforecast.dailyforecast.data.SearchInput
import javax.inject.Inject

interface SearchDailyForecastUseCase {
    suspend fun search(searchQuery: SearchInput): Result<SearchResponse>
}

class SearchDailyForecastUseCaseImpl @Inject constructor(
    private val repo: DailyForecastRepo
) : SearchDailyForecastUseCase {
    override suspend fun search(searchQuery: SearchInput): Result<SearchResponse> {
        return repo.search(searchQuery)
    }
}
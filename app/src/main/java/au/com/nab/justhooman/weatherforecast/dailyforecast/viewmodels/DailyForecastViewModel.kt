package au.com.nab.justhooman.weatherforecast.dailyforecast.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.nab.justhooman.weatherforecast.dailyforecast.api.dto.DailyForecast
import au.com.nab.justhooman.weatherforecast.dailyforecast.data.SearchInput
import au.com.nab.justhooman.weatherforecast.dailyforecast.usecases.SearchDailyForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyForecastViewModel @Inject constructor(
    private val search: SearchDailyForecastUseCase
) : ViewModel() {
    private val _searchResults = MutableLiveData<Pair<SearchInput, List<DailyForecast>>>()
    val searchResults: LiveData<Pair<SearchInput, List<DailyForecast>>> = _searchResults

    private val _loading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData(false)
    val isError: LiveData<Boolean> = _error

    fun handledError() {
        _error.value = false
    }

    fun search(searchQuery: SearchInput) {
        viewModelScope.launch {
            _loading.value = true
            val newDataSet = search.search(searchQuery)
                .onFailure {
                    _error.value = true
                }
                .getOrNull()?.list.orEmpty()
            _loading.value = false
            _searchResults.value = searchQuery to newDataSet
        }
    }
}
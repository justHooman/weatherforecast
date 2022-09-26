package au.com.nab.justhooman.weatherforecast.dailyforecast.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import au.com.nab.justhooman.weatherforecast.MainCoroutineRule
import au.com.nab.justhooman.weatherforecast.dailyforecast.usecases.SearchDailyForecastUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.mockito.Mockito

@HiltAndroidTest
class DailyForecastViewModelTest {

    private lateinit var viewModel: DailyForecastViewModel
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val coroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)
        .around(coroutineRule)

    @Before
    fun setUp() {
        hiltRule.inject()

        val searchUseCase = Mockito.mock(SearchDailyForecastUseCase::class.java)

        viewModel = DailyForecastViewModel(searchUseCase)
    }

    @Test
    fun search() {
//        viewModel.search()
    }
}
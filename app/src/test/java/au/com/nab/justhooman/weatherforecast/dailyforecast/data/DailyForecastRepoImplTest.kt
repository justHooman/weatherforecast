package au.com.nab.justhooman.weatherforecast.dailyforecast.data

import androidx.collection.lruCache
import au.com.nab.justhooman.weatherforecast.dailyforecast.api.dto.SearchResponse
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Provider

@RunWith(MockitoJUnitRunner::class)
class DailyForecastRepoImplTest {

    private lateinit var repo: DailyForecastRepo
    private lateinit var networkRepo: DailyForecastRepo

    @Before
    fun setUp() {
        networkRepo = mock(DailyForecastRepo::class.java)
        val provider = mock(Provider::class.java)
        doReturn(networkRepo).`when`(provider).get()
        repo = DailyForecastRepoImpl(provider as Provider<DailyForecastRepo>)
    }

    @Test
    fun searchShouldTriggerNetworkRequestWhenCacheMiss() {
        runBlocking {
            val inputs = listOf("a", "b", "c")
                .map { SearchInput(query = it, appId = "abc") }
            inputs.forEach {
                doReturn(SearchResponse()).`when`(networkRepo).search(it)
                repo.search(it)
                verify(networkRepo, times(1)).search(it)
            }
        }
    }

    @Test
    fun searchShouldTriggerNetworkRequestWhenCacheHit() {
        runBlocking {
            val input1 = SearchInput(query = "a", appId = "abc")
            val input2 = SearchInput(query = "b", appId = "abc")

            doReturn(SearchResponse()).`when`(networkRepo).search(
                input1)
            doReturn(SearchResponse()).`when`(networkRepo).search(
                input2)

            repo.search(input1)
            repo.search(input2)
            repo.search(input2)
            repo.search(input1)

            verify(networkRepo, times(1)).search(input1)
            verify(networkRepo, times(1)).search(input2)
        }
    }
}
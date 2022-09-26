package au.com.nab.justhooman.weatherforecast.dailyforecast.data

import org.junit.Assert.*

import org.junit.Test

class SearchInputKtTest {

    @Test
    fun toQueryMapShouldReturnCorrectMap() {
        val input = SearchInput("query", 1, "appId", TemperatureUnit.Fahrenheit)
        val map = input.toQueryMap()
        assertEquals("query", map["q"])
        assertEquals("1", map["cnt"])
        assertEquals("appId", map["appid"])
        assertEquals("imperial", map["units"])
    }

    @Test
    fun toQueryMapShouldCountValueInCorrectRange() {
        var input = SearchInput("query", -100, "appId", TemperatureUnit.Fahrenheit)
        var map = input.toQueryMap()
        assertEquals(Config.queryDayMin.toString(), map["cnt"])

        input = SearchInput("query", 100, "appId", TemperatureUnit.Fahrenheit)
        map = input.toQueryMap()
        assertEquals(Config.queryDayMax.toString(), map["cnt"])
    }
}
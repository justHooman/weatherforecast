package au.com.nab.justhooman.weatherforecast.dailyforecast.ui.adapter

import au.com.nab.justhooman.weatherforecast.dailyforecast.data.Config
import au.com.nab.justhooman.weatherforecast.dailyforecast.data.TemperatureUnit
import org.junit.Assert.*

import org.junit.Test

class ConvertersKtTest {

    @Test
    fun toSearchInputShouldReturnValueFromInput() {
        val queries = "saigon , day=10, unit=c"
        val searchInput = queries.toSearchInput()

        assertEquals("saigon", searchInput.query)
        assertEquals(10, searchInput.count)
        assertEquals(TemperatureUnit.Celsius, searchInput.units)
    }

    @Test
    fun toSearchInputShouldReturnDefaultValueForOptionalInput() {
        val queries = "saigon"
        val searchInput = queries.toSearchInput()

        assertEquals("saigon", searchInput.query)
        assertEquals(Config.queryDayDefault, searchInput.count)
        assertEquals(TemperatureUnit.Kelvin, searchInput.units)
    }

    @Test
    fun parseDayCountShouldReturnValueFromOption() {
        val queries = listOf("", "day=10", "")
        val count = parseDayCount(queries)
        assertEquals(10, count)
    }

    @Test
    fun parseDayCountShouldReturnDefaultWhenOptionIsMissing() {
        val queries = listOf("")
        val count = parseDayCount(queries)
        assertEquals(Config.queryDayDefault, count)
    }

    @Test
    fun parseDayCountShouldReturnDefaultWhenOptionIsInvalid() {
        val queries = listOf("", "day=a", "")
        val count = parseDayCount(queries)
        assertEquals(Config.queryDayDefault, count)
    }

    @Test
    fun parseDayCountShouldReturnTheFirstOptionFound() {
        val queries = listOf("", "day=10", "day=11")
        val count = parseDayCount(queries)
        assertEquals(10, count)
    }

    @Test
    fun parseTemperatureUnitShouldReturnValueFromOption() {
        val queries = listOf("", "unit=c", "")
        val unit = parseTemperatureUnit(queries)
        assertEquals(TemperatureUnit.Celsius, unit)
    }

    @Test
    fun parseTemperatureUnitShouldDefaultWhenOptionIsMissing() {
        val queries = listOf("")
        val unit = parseTemperatureUnit(queries)
        assertEquals(TemperatureUnit.Kelvin, unit)
    }

    @Test
    fun parseTemperatureUnitShouldDefaultWhenOptionIsInvalid() {
        val queries = listOf("", "unit=a", "")
        val unit = parseTemperatureUnit(queries)
        assertEquals(TemperatureUnit.Kelvin, unit)
    }

    @Test
    fun parseTemperatureUnitShouldReturnTheFirstOptionFound() {
        val queries = listOf("", "unit=c", "unit=k")
        val unit = parseTemperatureUnit(queries)
        assertEquals(TemperatureUnit.Celsius, unit)
    }
}
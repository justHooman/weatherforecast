package au.com.nab.justhooman.weatherforecast.dailyforecast.data

import org.junit.Assert.*

import org.junit.Test

class TemperatureUnitKtTest {

    @Test
    fun toQueryShouldReturnDataMappedWithEnum() {
        assertEquals("metric", TemperatureUnit.Celsius.toQuery())
        assertEquals("imperial", TemperatureUnit.Fahrenheit.toQuery())
        assertEquals("standard", TemperatureUnit.Kelvin.toQuery())
    }
}
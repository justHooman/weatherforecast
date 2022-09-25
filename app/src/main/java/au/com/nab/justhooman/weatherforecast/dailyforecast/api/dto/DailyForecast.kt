package au.com.nab.justhooman.weatherforecast.dailyforecast.api.dto


import com.google.gson.annotations.SerializedName

data class DailyForecast (
  @SerializedName("dt")
  val dt: Long? = 0L,
  @SerializedName("humidity")
  val humidity: Int? = 0,
  @SerializedName("pressure")
  val pressure: Int? = 0,
  @SerializedName("temp")
  val temp: Temp? = Temp(),
  @SerializedName("weather")
  val weather: List<Weather>? = listOf()
)
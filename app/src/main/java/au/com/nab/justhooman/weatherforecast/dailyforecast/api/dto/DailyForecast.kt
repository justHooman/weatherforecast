package au.com.nab.justhooman.weatherforecast.dailyforecast.api.dto


import com.google.gson.annotations.SerializedName

data class DailyForecast(
  @SerializedName("dt")
  val dt: Int? = null,
  @SerializedName("humidity")
  val humidity: Int? = null,
  @SerializedName("pressure")
  val pressure: Double? = null,
  @SerializedName("temp")
  val temp: Temp? = null,
  @SerializedName("weather")
  val weather: List<Weather?>? = null
)
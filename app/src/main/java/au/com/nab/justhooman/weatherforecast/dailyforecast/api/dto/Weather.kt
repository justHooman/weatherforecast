package au.com.nab.justhooman.weatherforecast.dailyforecast.api.dto


import com.google.gson.annotations.SerializedName

data class Weather(
  @SerializedName("description")
  val description: String? = null
)
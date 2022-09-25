package au.com.nab.justhooman.weatherforecast.dailyforecast.api.dto


import com.google.gson.annotations.SerializedName

data class Temp(
  @SerializedName("day")
  val day: Double? = 0.0
)
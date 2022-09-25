package au.com.nab.justhooman.weatherforecast.dailyforecast.api.dto


import com.google.gson.annotations.SerializedName

data class SearchResponse(
  @SerializedName("cod")
  val cod: String? = null,
  @SerializedName("list")
  val list: List<DailyForecast>? = null
)
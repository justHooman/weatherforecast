package au.com.nab.justhooman.weatherforecast.dailyforecast.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import au.com.nab.justhooman.weatherforecast.R
import au.com.nab.justhooman.weatherforecast.dailyforecast.api.dto.DailyForecast
import au.com.nab.justhooman.weatherforecast.dailyforecast.data.SearchInput
import au.com.nab.justhooman.weatherforecast.dailyforecast.data.TemperatureUnit
import dagger.hilt.android.qualifiers.ActivityContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class DailyForecastAdapter @Inject constructor(
    private val converter: Converter
) :
    RecyclerView.Adapter<DailyForecastAdapter.ViewHolder>()
{

    interface Item {
        fun getDate(): CharSequence
        fun getAvgTemperature(): CharSequence
        fun getPressure(): CharSequence
        fun getHumidity(): CharSequence
        fun getDescription(): CharSequence
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textviewDate: TextView = view.findViewById(R.id.textview_date)
        val textviewAvgTemperature: TextView = view.findViewById(R.id.textview_avg_temperature)
        val textviewPressure: TextView = view.findViewById(R.id.textview_pressure)
        val textviewHumidity: TextView = view.findViewById(R.id.textview_humidity)
        val textviewDescription: TextView = view.findViewById(R.id.textview_description)
    }

    private var dataSet: List<Item> = emptyList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.daily_forecast_item_view, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val item = dataSet[position]
        viewHolder.textviewDate.text = item.getDate()
        viewHolder.textviewAvgTemperature.text = item.getAvgTemperature()
        viewHolder.textviewPressure.text = item.getPressure()
        viewHolder.textviewHumidity.text = item.getHumidity()
        viewHolder.textviewDescription.text = item.getDescription()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun updateDataSet(searchResults: Pair<SearchInput, List<DailyForecast>>) {
        converter.query = searchResults.first
        dataSet = searchResults.second.map { dailyForecast ->
            DailyForecastAdapterItemImpl(converter, dailyForecast)
        }
        notifyDataSetChanged()
    }
}

class DailyForecastAdapterItemImpl(
    private val converter: Converter,
    private val raw: DailyForecast
) : DailyForecastAdapter.Item {
    override fun getDate(): CharSequence {
        return converter.context.getString(
            R.string.daily_forecast_item_view_date,
            raw.dt?.let { dt -> Date(dt * 1000L) }?.let { converter.dateFormat.format(it) }
        )
    }

    override fun getAvgTemperature(): CharSequence {
        return converter.context.getString(
            R.string.daily_forecast_item_view_avg_temperature,
            raw.temp?.day?.toFloat() ?: 0f,
            converter.query.toUnit()
        )
    }

    private fun SearchInput?.toUnit(): String {
        return when (this?.units) {
            TemperatureUnit.Celsius -> "°C"
            TemperatureUnit.Fahrenheit -> "°F"
            else -> "K"
        }
    }

    override fun getPressure(): CharSequence = converter.context.getString(
        R.string.daily_forecast_item_view_pressure,
        raw.pressure?.toFloat() ?: 0f
    )

    override fun getHumidity(): CharSequence = converter.context.getString(
        R.string.daily_forecast_item_view_humidity,
        raw.humidity
    )

    override fun getDescription(): CharSequence =
        converter.context.getString(
            R.string.daily_forecast_item_view_description,
            raw.weather?.firstOrNull()?.description.toString()
        )
}

class Converter @Inject constructor(@ActivityContext val context: Context) {
    val dateFormat: SimpleDateFormat by lazy { SimpleDateFormat("EEE, dd MMM yyyy") }
    var query: SearchInput? = null
}
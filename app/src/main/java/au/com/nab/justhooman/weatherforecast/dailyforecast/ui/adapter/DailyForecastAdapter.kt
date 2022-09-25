package au.com.nab.justhooman.weatherforecast.dailyforecast.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import au.com.nab.justhooman.weatherforecast.R

class DailyForecastAdapter(private val dataSet: List<Item>) : RecyclerView.Adapter<DailyForecastAdapter.ViewHolder>() {
    interface Item {
        fun getDate(): CharSequence
        fun getAvgTemperature(): CharSequence
        fun getPressure(): CharSequence
        fun getHumidity(): CharSequence
        fun getDescription(): CharSequence
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textviewDate: TextView
        val textviewAvgTemperature: TextView
        val textviewPressure: TextView
        val textviewHumidity: TextView
        val textviewDescription: TextView
        init {
            textviewDate = view.findViewById(R.id.textview_date)
            textviewAvgTemperature = view.findViewById(R.id.textview_avg_temperature)
            textviewPressure = view.findViewById(R.id.textview_pressure)
            textviewHumidity = view.findViewById(R.id.textview_humidity)
            textviewDescription = view.findViewById(R.id.textview_description)
        }
    }

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
}
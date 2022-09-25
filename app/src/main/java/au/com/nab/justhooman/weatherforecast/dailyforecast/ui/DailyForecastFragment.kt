package au.com.nab.justhooman.weatherforecast.dailyforecast.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import au.com.nab.justhooman.weatherforecast.BuildConfig
import au.com.nab.justhooman.weatherforecast.R
import au.com.nab.justhooman.weatherforecast.dailyforecast.data.Config
import au.com.nab.justhooman.weatherforecast.dailyforecast.data.SearchInput
import au.com.nab.justhooman.weatherforecast.dailyforecast.data.TemperatureUnit
import au.com.nab.justhooman.weatherforecast.dailyforecast.ui.adapter.DailyForecastAdapter
import au.com.nab.justhooman.weatherforecast.dailyforecast.viewmodels.DailyForecastViewModel
import au.com.nab.justhooman.weatherforecast.databinding.DailyForecastFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DailyForecastFragment : Fragment() {

    private var _binding: DailyForecastFragmentBinding? = null

    private val viewModel: DailyForecastViewModel by viewModels()

    @Inject lateinit var dailyForecastAdapter: DailyForecastAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DailyForecastFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewSearchResult.apply {
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
            adapter = dailyForecastAdapter
        }

        viewModel.searchResults.observe(viewLifecycleOwner) { searchResults ->
            (binding.recyclerViewSearchResult.adapter as? DailyForecastAdapter)?.updateDataSet(searchResults)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressbarLoading.isVisible = isLoading
        }

        viewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                viewModel.handledError()
                Snackbar.make(
                    binding.root,
                    getString(R.string.daily_forecast_warning_common_error),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        binding.textviewSearch.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_NEXT -> {
                    binding.buttonGetWeather.performClick()
                    false
                }
                else -> false
            }
        }

        binding.buttonGetWeather.setOnClickListener {
            hideKeyBoard(view)
            val rawQuery = binding.textviewSearch.text.toString().trim()
            val minLength = Config.inputMinLength
            if (rawQuery.length < minLength) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.daily_forecast_warning_input_min_length, minLength),
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            viewModel.search(rawQuery.toSearchInput())
        }
    }

    private fun String.toSearchInput(): SearchInput {
        val queries = this.split(",").map { it.trim() }
        val query = queries.firstOrNull().orEmpty()
        val countPrefix = "day="
        val count = queries.firstOrNull { it.startsWith(countPrefix) }
            ?.substring(countPrefix.length)?.toIntOrNull() ?: Config.queryDayDefault
        val unitPrefix = "unit="
        val unit = queries.firstOrNull { it.startsWith(unitPrefix) }
            ?.substring(unitPrefix.length).let { unit ->
                when (unit) {
                    "c" -> TemperatureUnit.Celsius
                    "f" -> TemperatureUnit.Fahrenheit
                    else -> TemperatureUnit.Kelvin
                }
            }
        return SearchInput(
            appId =  BuildConfig.OPEN_WEATHER_MAP_APPID,
            query = query,
            count = count,
            units = unit
        )
    }

    private fun hideKeyBoard(view: View) {
        binding.textviewSearch.apply {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            clearFocus()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package au.com.nab.justhooman.weatherforecast.dailyforecast.di

import au.com.nab.justhooman.weatherforecast.dailyforecast.data.DailyForecastRepo
import au.com.nab.justhooman.weatherforecast.dailyforecast.data.DailyForecastRepoImpl
import au.com.nab.justhooman.weatherforecast.dailyforecast.data.DailyForecastRepoNetworkImpl
import au.com.nab.justhooman.weatherforecast.dailyforecast.data.RepoType
import au.com.nab.justhooman.weatherforecast.dailyforecast.ui.adapter.Converter
import au.com.nab.justhooman.weatherforecast.dailyforecast.ui.adapter.DailyForecastAdapter
import au.com.nab.justhooman.weatherforecast.dailyforecast.usecases.SearchDailyForecastUseCase
import au.com.nab.justhooman.weatherforecast.dailyforecast.usecases.SearchDailyForecastUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class DailyForecastModule {

    @Binds
    abstract fun bindDailyForecastRepo(impl: DailyForecastRepoImpl) : DailyForecastRepo

    @Binds
    @Named(RepoType.NETWORK)
    abstract fun bindDailyForecastNetworkRepo(impl: DailyForecastRepoNetworkImpl) : DailyForecastRepo

    @Binds
    abstract fun bindSearchDailyForecastUseCase(impl: SearchDailyForecastUseCaseImpl) : SearchDailyForecastUseCase
}

@Module
@InstallIn(FragmentComponent::class)
class DailyForecastProviders {

    @FragmentScoped
    @Provides
    fun provideDailyForecastAdapter(converter: Converter) : DailyForecastAdapter {
        return DailyForecastAdapter(converter = converter)
    }
}

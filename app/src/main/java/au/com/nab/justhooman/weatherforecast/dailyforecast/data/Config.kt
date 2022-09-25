package au.com.nab.justhooman.weatherforecast.dailyforecast.data

object Config {
    const val inputMinLength = 3
    const val queryDayMin = 1
    const val queryDayMax = 16
    const val queryDayDefault = 7

    const val cachedQueriesSize = 20
}

object RepoType {
    const val MEM_CACHED = "mem_cached"
    const val NETWORK = "network"
}
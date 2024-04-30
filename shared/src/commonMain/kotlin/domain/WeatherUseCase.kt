package domain

import kotlinx.coroutines.flow.Flow
import models.ResultType
import models.WeatherReport
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

class WeatherUseCase() {
    val di = DI {
        bindSingleton<WeatherRepository> { WeatherRepository() }
    }

    private val weatherRepository: WeatherRepository by di.instance()
    suspend operator fun invoke(location: String): Flow<ResultType<out WeatherReport>> =
        weatherRepository.getWeatherReport(location)
}
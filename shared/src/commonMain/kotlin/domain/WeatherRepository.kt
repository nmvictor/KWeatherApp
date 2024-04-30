package domain

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import models.ResultType
import models.WeatherReport
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

class WeatherRepository internal constructor() {

    val di = DI {
        bindSingleton<WeatherApi> { WeatherApi() }
    }

    private val weatherApi: WeatherApi by di.instance()
    suspend fun getWeatherReport(location: String) = flow<ResultType<out WeatherReport>> {
        weatherApi.getWeatherReport(location)
            .onSuccess {
                println(it)
                emit(ResultType.Success(it))
            }.onFailure {
                println(it.message)
                emit(ResultType.Error(it.message))
            }
    }.onStart {
        emit(ResultType.Loading())
    }
}
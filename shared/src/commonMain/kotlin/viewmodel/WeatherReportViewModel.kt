package viewmodel

import domain.WeatherUseCase
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.WeatherRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import models.ResultType
import models.WeatherReport
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val di = DI {
    bindSingleton<WeatherUseCase> { WeatherUseCase() }
}

class WeatherReportViewModel constructor() : ViewModel() {

    private val weatherUseCase: WeatherUseCase by di.instance()
    private val weatherReportMutable =
        MutableStateFlow<ResultType<WeatherReport>>(ResultType.Loading())
    val weather = weatherReportMutable.asStateFlow()

    private val selectedWeatherReportMutable = MutableStateFlow<WeatherReport?>(null)
    val selected = selectedWeatherReportMutable.asStateFlow()

    init {
        loadWeatherReport("Nairobi")
    }

    fun loadWeatherReport(location: String) {
        weatherReportMutable.value = ResultType.Loading()
        viewModelScope.launch {
            try {
                weatherUseCase(location = location).collect{ report ->
                    if (report.error !== null) {
                        report.error.let {
                            weatherReportMutable.value = ResultType.Error(it)
                        }
                    } else {
                        report.data.let {
                            weatherReportMutable.value = ResultType.Success(it)
                        }
                    }


                }

            } catch (e: Exception) {
                e.printStackTrace()
                weatherReportMutable.value = ResultType.Error(e.message)
            }
        }
    }

    fun selectWeatherReport(report: WeatherReport) {
        selectedWeatherReportMutable.value = report
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}
import domain.WeatherApi
import domain.WeatherRepository
import domain.WeatherUseCase
import org.koin.dsl.module

private val dataModule = module {
    single<WeatherApi> { WeatherApi() }
    single<WeatherRepository> { WeatherRepository() }
    single<WeatherUseCase> { WeatherUseCase() }
}


private val sharedModules = listOf(dataModule)

fun getSharedModules() = sharedModules
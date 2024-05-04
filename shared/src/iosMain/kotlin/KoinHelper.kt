import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import viewmodel.WeatherReportViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.component.get
import org.koin.dsl.module
import kotlin.native.concurrent.freeze

fun initKoin() {
    startKoin {
        modules(getSharedModules())
    }
}

class KoinHelper: KoinComponent {
    fun getAppViewModel() = get<WeatherReportViewModel>()
}

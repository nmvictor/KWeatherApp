package domain

import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import models.RespError
import models.ResultType
import models.WeatherReport
import networking.KtorHttpClient

internal class WeatherApi() {

    suspend fun getWeatherReport(location: String): Result<WeatherReport> = runCatching {
        val response: HttpResponse = KtorHttpClient.getClient().get(KtorHttpClient.BASE_URL +"/current.json") {
            url {
                parameters.append("key", "550881d62e1644948d0135017242804")
                parameters.append("q", location)
                headers.append("key", "550881d62e1644948d0135017242804")
                headers.append("Accept", "application/json")
            }
        }.body()
        println("getWeatherReport resp: " + response)
        if (response.status.value !== 200) {
            val error = response.body<RespError>()
            throw Exception(error.error.message)
        }

        return@runCatching response.body<WeatherReport>();
    }
}
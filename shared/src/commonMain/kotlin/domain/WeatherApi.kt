package domain

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import models.RespError
import models.WeatherReport
import networking.BASE_URL
import networking.httpClient

internal class WeatherApi() {

    suspend fun getWeatherReport(location: String): Result<WeatherReport> = runCatching {
        val response: HttpResponse = httpClient.get(BASE_URL +"/current.json") {
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
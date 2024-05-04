package networking

import io.ktor.client.HttpClient

internal const val BASE_URL = "https://api.weatherapi.com/v1"

expect val httpClient: HttpClient

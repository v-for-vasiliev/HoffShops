package ru.vasiliev.hoffshops.network

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

interface ClientProvider {
    fun provideDefaultClient(): HttpClient
}

class DefaultClientProvider : ClientProvider {

    override fun provideDefaultClient(): HttpClient {
        return HttpClient(Android) {
            engine {
                socketTimeout = 20_000
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                    coerceInputValues = true
                }, ContentType.parse("text/html"))
            }
            defaultRequest {
                url("https://hoff.ru/")
            }
        }
    }
}
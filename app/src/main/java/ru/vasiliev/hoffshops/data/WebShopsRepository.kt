package ru.vasiliev.hoffshops.data

import androidx.collection.LruCache
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.vasiliev.hoffshops.data.response.ShopsResponse
import ru.vasiliev.hoffshops.domain.entity.CityShopsEntity
import ru.vasiliev.hoffshops.domain.repository.ShopsRepository
import ru.vasiliev.hoffshops.network.ClientProvider
import java.net.URLEncoder

class WebShopsRepository(clientProvider: ClientProvider) : ShopsRepository {

    companion object {
        const val CACHE_SIZE = 1
        const val CACHE_KEY_SHOPS = "shops"
    }

    private val client by lazy {
        clientProvider.provideDefaultClient()
    }

    private val cache: LruCache<String, Any> = LruCache(CACHE_SIZE)

    override suspend fun getShops(): List<CityShopsEntity> {
        cache.get(CACHE_KEY_SHOPS)?.let { cachedResponse ->
            (cachedResponse as? ShopsResponse)?.let { return it.toShopsEntity() }
        }
        delay(1000L) // Fake delay to show loader
        val entities = URLEncoder.encode(Json.encodeToString(Entities("shops_by_cities")), "UTF-8")
        return client.get("vue/get-entities/?entities[]=$entities")
            .body<ShopsResponse>()
            .also {
                cache.put(CACHE_KEY_SHOPS, it)
            }
            .toShopsEntity()
    }

    override fun clearCache() {
        cache.remove(CACHE_KEY_SHOPS)
    }
}

@Serializable
data class Entities(
    val name: String
)
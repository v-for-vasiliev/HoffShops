package ru.vasiliev.hoffshops.domain.repository

import ru.vasiliev.hoffshops.domain.entity.CityShopsEntity

interface ShopsRepository {

    suspend fun getShops(): List<CityShopsEntity>

    fun clearCache()
}
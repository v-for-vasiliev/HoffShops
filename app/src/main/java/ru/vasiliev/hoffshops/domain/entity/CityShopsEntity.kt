package ru.vasiliev.hoffshops.domain.entity

data class CityShopsEntity(
    val city: String,
    val shops: List<ShopEntity>
)

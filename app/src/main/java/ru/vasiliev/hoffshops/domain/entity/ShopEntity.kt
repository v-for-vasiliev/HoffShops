package ru.vasiliev.hoffshops.domain.entity

data class ShopEntity(
    val name: String,
    val phone: String,
    val subway: List<SubwayEntity>
)
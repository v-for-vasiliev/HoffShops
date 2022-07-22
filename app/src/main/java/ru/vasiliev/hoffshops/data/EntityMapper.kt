package ru.vasiliev.hoffshops.data

import ru.vasiliev.hoffshops.data.response.ShopsResponse
import ru.vasiliev.hoffshops.domain.entity.CityShopsEntity
import ru.vasiliev.hoffshops.domain.entity.ShopEntity
import ru.vasiliev.hoffshops.domain.entity.SubwayEntity

fun ShopsResponse.toShopsEntity(): List<CityShopsEntity> {
    return this.data.shopsByCities.map { cityShops ->
        CityShopsEntity(
            cityShops.name,
            cityShops.shops.map { shop ->
                ShopEntity(
                    shop.shopName ?: "Неизвестный город",
                    shop.phone ?: "Телефон не указан",
                    shop.subway?.map {
                        SubwayEntity(
                            it.name,
                            it.color
                        )
                    } ?: emptyList()
                )
            }
        )
    }
}

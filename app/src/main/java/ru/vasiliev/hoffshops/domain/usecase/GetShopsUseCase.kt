package ru.vasiliev.hoffshops.domain.usecase

import ru.vasiliev.base.UseCase
import ru.vasiliev.hoffshops.domain.entity.ShopEntity
import ru.vasiliev.hoffshops.domain.repository.ShopsRepository

class GetShopsUseCase(
    private val repo: ShopsRepository
) : UseCase<String, List<ShopEntity>>() {

    override suspend fun execute(param: String): List<ShopEntity> {
        return repo.getShops().firstOrNull { it.city == param }?.shops ?: emptyList()
    }
}
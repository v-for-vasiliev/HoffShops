package ru.vasiliev.hoffshops.domain.usecase

import ru.vasiliev.base.UseCase
import ru.vasiliev.hoffshops.domain.entity.CityShopsEntity
import ru.vasiliev.hoffshops.domain.repository.ShopsRepository

class GetCitiesUseCase(
    private val repo: ShopsRepository
) : UseCase<Boolean, List<String>>() {

    override suspend fun execute(param: Boolean): List<String> {
        if (param) {
            repo.clearCache()
        }
        return repo.getShops().map { it.city }
    }
}
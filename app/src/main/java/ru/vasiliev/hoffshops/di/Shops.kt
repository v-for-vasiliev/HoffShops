package ru.vasiliev.hoffshops.di

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import ru.vasiliev.hoffshops.data.WebShopsRepository
import ru.vasiliev.hoffshops.domain.repository.ShopsRepository
import ru.vasiliev.hoffshops.domain.usecase.GetCitiesUseCase
import ru.vasiliev.hoffshops.domain.usecase.GetShopsUseCase
import ru.vasiliev.hoffshops.network.ClientProvider
import ru.vasiliev.hoffshops.presentation.ShopsActivity
import ru.vasiliev.hoffshops.presentation.cities.CitiesPresenter
import ru.vasiliev.hoffshops.presentation.shops.ShopsFragment
import ru.vasiliev.hoffshops.presentation.shops.ShopsPresenter
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ShopsScope

@ShopsScope
@Subcomponent(modules = [ShopsModule::class])
interface ShopsComponent {

    fun inject(view: ShopsFragment)
}

@Module
class ShopsModule {

    @ShopsScope
    @Provides
    fun provideShopsUseCase(shopsRepository: ShopsRepository) = GetShopsUseCase(shopsRepository)

    @ShopsScope
    @Provides
    fun provideShopsPresenter(shopsUseCase: GetShopsUseCase) = ShopsPresenter(shopsUseCase)
}
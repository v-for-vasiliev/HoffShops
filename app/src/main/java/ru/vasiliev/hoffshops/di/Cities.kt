package ru.vasiliev.hoffshops.di

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import ru.vasiliev.hoffshops.domain.repository.ShopsRepository
import ru.vasiliev.hoffshops.domain.usecase.GetCitiesUseCase
import ru.vasiliev.hoffshops.presentation.cities.CitiesFragment
import ru.vasiliev.hoffshops.presentation.cities.CitiesPresenter
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CitiesScope

@CitiesScope
@Subcomponent(modules = [CitiesModule::class])
interface CitiesComponent {

    fun inject(view: CitiesFragment)
}

@Module
class CitiesModule {

    @CitiesScope
    @Provides
    fun provideCitiesUseCase(shopsRepository: ShopsRepository) = GetCitiesUseCase(shopsRepository)

    @CitiesScope
    @Provides
    fun provideCitiesPresenter(citiesUseCase: GetCitiesUseCase) = CitiesPresenter(citiesUseCase)
}
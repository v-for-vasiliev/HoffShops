package ru.vasiliev.hoffshops.presentation.cities

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.MvpPresenter
import moxy.presenterScope
import ru.vasiliev.hoffshops.domain.usecase.GetCitiesUseCase

class CitiesPresenter(
    private val citiesUseCase: GetCitiesUseCase
) : MvpPresenter<CitiesView>() {

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        viewState.showError(exception)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadCities(true)
    }

    fun loadCities(clearCache: Boolean = false) {
        viewState.showLoader()
        presenterScope.launch(errorHandler) {
            citiesUseCase.execute(clearCache)
                .also {
                    withContext(Dispatchers.Main) {
                        viewState.showCities(it)
                    }
                }
        }
    }
}
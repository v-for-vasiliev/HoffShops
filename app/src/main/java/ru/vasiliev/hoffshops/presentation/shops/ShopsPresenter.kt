package ru.vasiliev.hoffshops.presentation.shops

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.MvpPresenter
import moxy.presenterScope
import ru.vasiliev.hoffshops.domain.usecase.GetShopsUseCase

class ShopsPresenter(
    private val shopsUseCase: GetShopsUseCase
) : MvpPresenter<ShopsView>() {

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        viewState.showError(exception)
    }

    fun loadShopsByCity(city: String) {
        viewState.showLoader()
        presenterScope.launch(errorHandler) {
            shopsUseCase.execute(city).also { cityShops ->
                withContext(Dispatchers.Main) {
                    viewState.showShops(cityShops)
                }
            }
        }
    }
}
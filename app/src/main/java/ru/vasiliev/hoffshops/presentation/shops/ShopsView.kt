package ru.vasiliev.hoffshops.presentation.shops

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import ru.vasiliev.hoffshops.domain.entity.ShopEntity

interface ShopsView : MvpView {

    @AddToEnd
    fun showLoader()

    @AddToEnd
    fun showShops(shops: List<ShopEntity>)

    @AddToEnd
    fun showError(error: Throwable)
}
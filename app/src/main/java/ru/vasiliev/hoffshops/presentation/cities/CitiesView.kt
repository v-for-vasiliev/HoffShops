package ru.vasiliev.hoffshops.presentation.cities

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle
import java.lang.Exception

interface CitiesView : MvpView {

    @AddToEnd
    fun showLoader()

    @AddToEnd
    fun showCities(cities: List<String>)

    @AddToEndSingle
    fun openCityShop(city: String)

    @AddToEnd
    fun showError(error: Throwable)
}
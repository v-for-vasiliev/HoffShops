package ru.vasiliev.hoffshops.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import ru.vasiliev.hoffshops.App
import ru.vasiliev.hoffshops.R
import ru.vasiliev.hoffshops.di.ShopsScope
import ru.vasiliev.hoffshops.presentation.cities.CitiesFragment
import ru.vasiliev.hoffshops.presentation.cities.CitiesPresenter
import ru.vasiliev.hoffshops.presentation.shops.ShopsFragment
import ru.vasiliev.hoffshops.presentation.shops.ShopsPresenter
import javax.inject.Inject
import javax.inject.Provider

interface ShopsScreen {

    fun openShops(city: String)
}

class ShopsActivity : AppCompatActivity(), ShopsScreen {

    private val citiesFragment by lazy {
        CitiesFragment()
    }

    private val shopsFragment by lazy {
        ShopsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.cities_container_view, citiesFragment)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }
    }

    override fun openShops(city: String) {
        supportFragmentManager.commit {
            replace(R.id.cities_container_view, shopsFragment.apply {
                arguments = Bundle().apply {
                    putString("city", city)
                }
            })
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }
}
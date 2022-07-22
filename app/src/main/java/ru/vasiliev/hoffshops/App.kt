package ru.vasiliev.hoffshops

import android.app.Application
import ru.vasiliev.hoffshops.di.AppComponent
import ru.vasiliev.hoffshops.di.AppModule
import ru.vasiliev.hoffshops.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
package ru.vasiliev.hoffshops.di

import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.vasiliev.hoffshops.data.WebShopsRepository
import ru.vasiliev.hoffshops.domain.repository.ShopsRepository
import ru.vasiliev.hoffshops.network.ClientProvider
import ru.vasiliev.hoffshops.network.DefaultClientProvider
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }

    fun getApplicationContext(): Context

    fun getShopsComponent(): ShopsComponent

    fun getCitiesComponent(): CitiesComponent
}

@Module
class AppModule(
    private val context: Context
) {

    @Singleton
    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    fun provideNetworkClient(): ClientProvider = DefaultClientProvider()

    @Singleton
    @Provides
    fun provideShopsRepository(clientProvider: ClientProvider): ShopsRepository =
        WebShopsRepository(clientProvider)
}
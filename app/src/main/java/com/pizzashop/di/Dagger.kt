package com.pizzashop.di

import com.pizzashop.ui.PizzaActivity
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        RetrofitModule::class,
        AppModule::class,
        RepositoryModule::class,
    ]
)
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }

    fun inject(activity: PizzaActivity)
}
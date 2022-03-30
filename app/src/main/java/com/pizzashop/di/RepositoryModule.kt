package com.pizzashop.di

import com.pizzashop.data.api.PizzaAPI
import com.pizzashop.data.repository.PizzaRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun getPizzaRepository(pizzaAPI: PizzaAPI): PizzaRepository =
        PizzaRepository(pizzaAPI)
}
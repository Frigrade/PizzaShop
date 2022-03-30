package com.pizzashop.di

import com.pizzashop.data.repository.PizzaRepository
import com.pizzashop.presentation.PizzaViewModelProviderFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(newsRepository: PizzaRepository): PizzaViewModelProviderFactory =
        PizzaViewModelProviderFactory(newsRepository)
}
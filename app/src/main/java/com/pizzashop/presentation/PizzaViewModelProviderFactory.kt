package com.pizzashop.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pizzashop.data.repository.PizzaRepository
import javax.inject.Inject

class PizzaViewModelProviderFactory @Inject constructor(
    private val pizzaRepository: PizzaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PizzaViewModel(pizzaRepository) as T
    }
}
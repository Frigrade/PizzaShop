package com.pizzashop.data.repository

import com.pizzashop.data.api.PizzaAPI
import javax.inject.Inject

class PizzaRepository @Inject constructor(
    private val api: PizzaAPI
) {
    suspend fun getNewPizza(countryCode: String) =
        api.getNewPizza(countryCode)
    }
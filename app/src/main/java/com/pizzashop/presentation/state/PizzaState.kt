package com.pizzashop.presentation.state

import com.pizzashop.domain.entity.PizzaResponse

sealed class PizzaState {
    object Loading : PizzaState()

    data class Content(val data: PizzaResponse) : PizzaState()
    data class Error(val message: String) : PizzaState()
}

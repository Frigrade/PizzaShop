package com.pizzashop.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pizzashop.data.repository.PizzaRepository
import com.pizzashop.domain.entity.PizzaResponse
import com.pizzashop.presentation.state.PizzaState
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


class PizzaViewModel @Inject constructor(
    private val PizzaRepository: PizzaRepository
) : ViewModel() {
    val newPizzaLiveData: MutableLiveData<PizzaState> = MutableLiveData()

    fun getNewPizza(countryCode: String) = viewModelScope.launch {
        newPizzaLiveData.postValue(PizzaState.Loading)
        val rawResponse = PizzaRepository.getNewPizza(countryCode)
        newPizzaLiveData.postValue(handleNewPizzaResponse(rawResponse))
    }

    private fun handleNewPizzaResponse(
        response: Response<PizzaResponse>,
    ): PizzaState {
        if (response.isSuccessful) {
            response.body()?.let {
                return PizzaState.Content(it)
            }
        }
        return PizzaState.Error(response.message())
    }

}
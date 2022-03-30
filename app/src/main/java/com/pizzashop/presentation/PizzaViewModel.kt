package com.pizzashop.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pizzashop.data.repository.PizzaRepository
import com.pizzashop.domain.entity.Article
import com.pizzashop.domain.entity.PizzaResponse
import com.pizzashop.presentation.state.PizzaState
import com.pizzashop.util.Constants.Companion.API_TOTAL_PAGES
import com.pizzashop.util.Constants.Companion.HTTP_TOO_MANY_REQUESTS
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


class PizzaViewModel @Inject constructor(
    private val PizzaRepository: PizzaRepository
) : ViewModel() {
    val newPizzaLiveData: MutableLiveData<PizzaState> = MutableLiveData()
    var newPizzaPage = 1
    var newPizzaResponse: PizzaResponse? = null
    
    fun getNewPizza(countryCode: String) = viewModelScope.launch {
        val currentArticleList = (newPizzaLiveData.value as? PizzaState.Content)?.data?.articles
        newPizzaLiveData.postValue(PizzaState.Loading)
        val rawResponse = PizzaRepository.getNewPizza(countryCode, newPizzaPage)
        newPizzaLiveData.postValue(handleNewPizzaResponse(rawResponse, currentArticleList))
    }

    private fun handleNewPizzaResponse(
        response: Response<PizzaResponse>,
        currentArticleList: MutableList<Article>?
    ): PizzaState {
        if (response.isSuccessful) {
            response.body()?.let {
                newPizzaPage++
                if (newPizzaPage > API_TOTAL_PAGES) {
                    newPizzaPage = API_TOTAL_PAGES
                }
                if (newPizzaResponse == null) {
                    newPizzaResponse = it
                } else {
                    val oldArticles = newPizzaResponse?.articles
                    val newArticles = it.articles
                    oldArticles?.addAll(newArticles)
                }
                return PizzaState.Content(newPizzaResponse ?: it)
            }
        }
        if (response.code() == HTTP_TOO_MANY_REQUESTS) {
            return PizzaState.Content(
                PizzaResponse(
                    articles = currentArticleList ?: mutableListOf(),
                    status = "",
                    totalResults = currentArticleList?.size ?: 0
                )
            )
        }
        return PizzaState.Error(response.message())
    }

}
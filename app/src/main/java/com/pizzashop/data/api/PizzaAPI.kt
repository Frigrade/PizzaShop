package com.pizzashop.data.api

import com.pizzashop.domain.entity.PizzaResponse
import com.pizzashop.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PizzaAPI {
    @GET("v2/top-headlines")
    suspend fun getNewPizza(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<PizzaResponse>
}
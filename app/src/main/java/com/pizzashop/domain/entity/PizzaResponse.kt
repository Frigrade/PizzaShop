package com.pizzashop.domain.entity

data class PizzaResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)

package com.shreyanshsinghks.search.data.repository

import com.shreyanshsinghks.search.data.remote.SearchApiService
import com.shreyanshsinghks.search.domain.models.Recipe
import com.shreyanshsinghks.search.domain.models.RecipeDetails
import com.shreyanshsinghks.search.domain.repository.SearchRepository


class SearchRepoImpl(private val searchApiService: SearchApiService) : SearchRepository {
    override suspend fun getRecipes(s: String): List<Recipe> {
        searchApiService.getRecipes(s)
    }

    override suspend fun getRecipeDetails(id: String): RecipeDetails {
        TODO("Not yet implemented")
    }

}
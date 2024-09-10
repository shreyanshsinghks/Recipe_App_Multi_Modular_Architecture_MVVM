package com.shreyanshsinghks.search.domain.repository

import com.shreyanshsinghks.search.domain.models.Recipe
import com.shreyanshsinghks.search.domain.models.RecipeDetails

interface SearchRepository {
    suspend fun getRecipes(s: String): List<Recipe>

    suspend fun getRecipeDetails(id: String): RecipeDetails
}
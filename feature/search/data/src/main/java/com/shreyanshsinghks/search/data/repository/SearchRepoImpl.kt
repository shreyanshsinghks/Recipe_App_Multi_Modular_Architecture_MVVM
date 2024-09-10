package com.shreyanshsinghks.search.data.repository

import com.shreyanshsinghks.search.data.mappers.toDomain
import com.shreyanshsinghks.search.data.remote.SearchApiService
import com.shreyanshsinghks.search.domain.models.Recipe
import com.shreyanshsinghks.search.domain.models.RecipeDetails
import com.shreyanshsinghks.search.domain.repository.SearchRepository


class SearchRepoImpl(private val searchApiService: SearchApiService) : SearchRepository {

    override suspend fun getRecipes(s: String): Result<List<Recipe>> {
        return try {
            val response = searchApiService.getRecipes(s)
            if (response.isSuccessful) {
                response.body()?.meals?.let {
                    Result.success(it.toDomain())
                } ?: run { Result.failure(Exception("No data found")) }
            } else {
                Result.failure(Exception("Error occurred"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun getRecipeDetails(id: String): Result<RecipeDetails> {
        return try {
            val response = searchApiService.getRecipeDetails(id)
            if (response.isSuccessful) {
                response.body()?.meals?.let {
                    if (it.isNotEmpty()) {
                        Result.success(it.first().toDomain())
                    } else {
                        Result.failure(Exception("No data found"))
                    }
                } ?: run { Result.failure(Exception("No data found")) }
            } else {
                Result.failure(Exception("Error occurred"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
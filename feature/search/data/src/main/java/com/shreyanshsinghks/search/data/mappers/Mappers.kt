package com.shreyanshsinghks.search.data.mappers

import com.shreyanshsinghks.search.data.model.RecipeDTO
import com.shreyanshsinghks.search.domain.models.Recipe

fun List<RecipeDTO>.toDomain(): List<Recipe> = map {
    Recipe(
        idMeal = it.idMeal,
        strArea = it.strArea,
        strMeal = it.strMeal,
        strMealThumb = it.strMealThumb,
        strCategory = it.strCategory,
        strTags = it.strTags ?: "",
        strYoutube = it.strYoutube ?: "",
        strInstructions = it.strInstructions ?: ""
    )
}
package com.shreyanshsinghks.search.domain.use_cases

import com.shreyanshsinghks.common.utils.NetworkResult
import com.shreyanshsinghks.search.domain.models.RecipeDetails
import com.shreyanshsinghks.search.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetRecipeDetailsUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    operator fun invoke(id: String) = flow<NetworkResult<RecipeDetails>> {
        val response = searchRepository.getRecipeDetails(id = id)
        if (response.isSuccess) {
            emit(NetworkResult.Success(data = response.getOrNull()))
        } else {
            emit(NetworkResult.Error(message = response.exceptionOrNull()?.message))
        }
    }.catch {
        emit(NetworkResult.Error(it.message))
    }.flowOn(Dispatchers.IO)
}
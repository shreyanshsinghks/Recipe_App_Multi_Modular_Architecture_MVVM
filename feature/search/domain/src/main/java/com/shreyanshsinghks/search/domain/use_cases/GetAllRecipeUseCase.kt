package com.shreyanshsinghks.search.domain.use_cases

import com.shreyanshsinghks.common.utils.NetworkResult
import com.shreyanshsinghks.search.domain.models.Recipe
import com.shreyanshsinghks.search.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllRecipeUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    operator fun invoke(q: String) = flow<NetworkResult<List<Recipe>>> {
        emit(NetworkResult.Loading())
        val response = searchRepository.getRecipes(q)
        if (response.isSuccess) {
            emit(NetworkResult.Success(data = response.getOrNull()))
        } else {
            emit(NetworkResult.Error(message = response.exceptionOrNull()?.message))
        }
    }.catch {
        emit(NetworkResult.Error(message = it.message.toString()))
    }.flowOn(Dispatchers.IO)
}
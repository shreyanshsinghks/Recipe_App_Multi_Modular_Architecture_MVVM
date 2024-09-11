package com.shreyanshsinghks.search.screens.recipe_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.shreyanshsinghks.common.utils.NetworkResult
import com.shreyanshsinghks.common.utils.UiText
import com.shreyanshsinghks.search.domain.models.Recipe
import com.shreyanshsinghks.search.domain.use_cases.GetAllRecipeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(
    private val getAllRecipeUseCase: GetAllRecipeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecipeList.UiState())
    val uiState: StateFlow<RecipeList.UiState> get() = _uiState.asStateFlow()

    fun onEvent(event: RecipeList.Event) {
        when (event) {
            is RecipeList.Event.SearchRecipe -> search(event.q)
        }
    }

    private fun search(q: String) = getAllRecipeUseCase.invoke(q)
        .onEach { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    _uiState.update { RecipeList.UiState(isLoading = true) }
                }

                is NetworkResult.Error -> {
                    _uiState.update { RecipeList.UiState(error = UiText.RemoteString(result.message.toString())) }

                }

                is NetworkResult.Success -> {
                    _uiState.update { RecipeList.UiState(data = result.data) }
                }
            }
        }.launchIn(viewModelScope)
}


object RecipeList {
    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: List<Recipe>? = emptyList()
    )

    sealed interface Navigation {
        data class GoToRecipeDetails(val id: String) : Navigation
    }

    sealed interface Event {
        data class SearchRecipe(val q: String) : Event
    }
}
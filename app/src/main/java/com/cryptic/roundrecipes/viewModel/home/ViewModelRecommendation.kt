package com.cryptic.roundrecipes.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cryptic.roundrecipes.database.Recipe
import com.cryptic.roundrecipes.database.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

sealed class RecipeListUiState {
    object Loading : RecipeListUiState()
    data class Success(val recipes: List<Recipe>) : RecipeListUiState()
    data class Error(val message: String) : RecipeListUiState()
}

class HomeViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<RecipeListUiState>(RecipeListUiState.Loading)
    val uiState: StateFlow<RecipeListUiState> = _uiState

    init {
        observeRecipes()
    }

    private fun observeRecipes() {
        _uiState.value = RecipeListUiState.Loading

        viewModelScope.launch {
            repository.getAllRecipes()
                .catch { exception ->
                    _uiState.value = RecipeListUiState.Error(exception.message ?: "An unknown error occurred")
                }
                .collect { recipeList ->
                    if (recipeList.isNotEmpty()) {
                        _uiState.value = RecipeListUiState.Success(recipeList)
                    }
                }
        }
    }
}

class HomeViewModelFactory(private val repository: RecipeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

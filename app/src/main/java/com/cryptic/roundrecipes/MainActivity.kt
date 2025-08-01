package com.cryptic.roundrecipes

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cryptic.roundrecipes.database.AppDatabase
import com.cryptic.roundrecipes.database.RecipeRepository
import com.cryptic.roundrecipes.screens.home.HomeScreen
import com.cryptic.roundrecipes.ui.theme.RoundRecipesTheme
import com.cryptic.roundrecipes.viewModel.home.HomeViewModel
import com.cryptic.roundrecipes.viewModel.home.HomeViewModelFactory
import com.cryptic.roundrecipes.viewModel.home.RecipeListUiState
import kotlin.getValue

class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels {

        val database = AppDatabase.getInstance(application)
        val repository = RecipeRepository(database.recipeDao())
        HomeViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoundRecipesTheme {
                val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
                HomeScreen(
                    uiState = uiState
                )
            }
        }
    }
}
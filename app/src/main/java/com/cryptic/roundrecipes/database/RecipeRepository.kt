package com.cryptic.roundrecipes.database

import kotlinx.coroutines.flow.Flow


class RecipeRepository(private val recipeDao: RecipeDao) {

    //add network request here

    fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()

    suspend fun getRecipesByCountry(countryCode: String) = recipeDao.getRecipesByCountry(countryCode)

    suspend fun getRecipeWithDetails(id: String): RecipeWithDetails? = recipeDao.getRecipeWithDetails(id)

    suspend fun getRecipeCount() = recipeDao.getRecipeCount()
}
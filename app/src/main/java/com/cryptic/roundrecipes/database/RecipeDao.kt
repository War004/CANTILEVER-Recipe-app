package com.cryptic.roundrecipes.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    // --- INSERTION ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(ingredients: List<Ingredient>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstructions(instructions: List<Instruction>)

    // Function to  insert everything in one transaction for safety
    @Transaction
    suspend fun insertFullRecipe(recipe: Recipe, ingredients: List<Ingredient>, instructions: List<Instruction>) {
        insertRecipe(recipe)
        insertIngredients(ingredients)
        insertInstructions(instructions)
    }

    // --- QUERIES ---
    @Transaction
    @Query("SELECT * FROM recipes WHERE recipeId = :id")
    suspend fun getRecipeWithDetails(id: String): RecipeWithDetails?

    @Query("SELECT * FROM recipes WHERE countryCode = :countryCode")
    suspend fun getRecipesByCountry(countryCode: String): List<Recipe>

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT COUNT(*) FROM recipes")
    suspend fun getRecipeCount(): Int
}
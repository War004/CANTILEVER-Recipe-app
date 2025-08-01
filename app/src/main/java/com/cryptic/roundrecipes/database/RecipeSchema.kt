package com.cryptic.roundrecipes.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Relation
import androidx.room.TypeConverter

@Entity(
    tableName = "recipes",
    // This index is the key to fast country-based searches
    indices = [Index(value = ["countryCode"])]
)
data class Recipe(
    @PrimaryKey val recipeId: String, // "hyderabadi_biryani"
    val countryCode: String,          // "in", "cn", "jp", etc.

    @Embedded(prefix = "name_")
    val name: RecipeName,

    @Embedded(prefix = "class_")
    val classification: Classification,

    val isNonVeg: Boolean,
    val totalTimeSeconds: Int,
    val warning: String?,
    val overloadInfo: String?
)

data class RecipeName(
    val local: String,
    val en: String
)

data class Classification(
    val course: List<String>,
    val category: List<String>,
    val mealTiming: List<String>
)

@Entity(
    tableName = "ingredients",
    // This links ingredients to a recipe and ensures they are deleted if the recipe is.
    foreignKeys = [ForeignKey(
        entity = Recipe::class,
        parentColumns = ["recipeId"],
        childColumns = ["recipeOwnerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val ingredientId: Int = 0,
    val recipeOwnerId: String, // This links back to Recipe.recipeId
    val name: String,
    val amount: Double?,
    val unit: String?,
    val availability: String?
)

@Entity(
    tableName = "instructions",
    foreignKeys = [ForeignKey(
        entity = Recipe::class,
        parentColumns = ["recipeId"],
        childColumns = ["recipeOwnerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Instruction(
    @PrimaryKey(autoGenerate = true) val instructionId: Int = 0,
    val recipeOwnerId: String, // Links back to Recipe.recipeId
    val step: Int,
    val description: String,
    val timeSeconds: Int,
    val isPassive: Boolean
)

class Converters {
    @TypeConverter
    fun fromStringToList(value: String?): List<String> {
        // Handles null or empty strings gracefully
        return value?.split(",")?.map { it.trim() } ?: emptyList()
    }

    @TypeConverter
    fun fromListToString(list: List<String>?): String {
        return list?.joinToString(",") ?: ""
    }
}

data class RecipeWithDetails(
    @Embedded
    val recipe: Recipe,

    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeOwnerId"
    )
    val ingredients: List<Ingredient>,

    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeOwnerId"
    )
    val instructions: List<Instruction>
)
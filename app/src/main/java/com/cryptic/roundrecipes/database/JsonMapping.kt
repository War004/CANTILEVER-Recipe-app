package com.cryptic.roundrecipes.database

import com.google.gson.annotations.SerializedName
import com.google.gson.JsonElement

data class CountryJsonModel(
    @SerializedName("recipes") val recipes: Map<String, RecipeJsonModel>,
    @SerializedName("lookups") val lookups: Map<String, Any>?, // Or a more specific class if needed
    @SerializedName("cat_two") val catTwo: List<String>?,
    @SerializedName("cat_three") val catThree: List<String>?
)

data class RecipeJsonModel(
    @SerializedName("name") val name: NameJsonModel,
    @SerializedName("is_nonVeg") val isNonVeg: Boolean,
    @SerializedName("total_time_seconds") val totalTimeSeconds: Int,
    @SerializedName("tags") val tags: List<String>?, // Optional, can be empty
    @SerializedName("warning") val warning: String,
    @SerializedName("overload_info") val overloadInfo: String?, // Optional
    @SerializedName("ingredients") val ingredients: List<IngredientJsonModel>,
    @SerializedName("instructions") val instructions: List<InstructionJsonModel>,
    @SerializedName("classification") val classification: ClassificationJsonModel,
    @SerializedName("regions") val regions: List<String>
)

data class NameJsonModel(
    @SerializedName("local") val local: JsonElement?, // Optional
    @SerializedName("en") val en: JsonElement
)

data class IngredientJsonModel(
    @SerializedName("name") val name: String,
    @SerializedName("amount") val amount: Double?, // Can be null
    @SerializedName("unit") val unit: String,
    @SerializedName("availability") val availability: String? // Can be null
)

data class InstructionJsonModel(
    @SerializedName("step") val step: Int,
    @SerializedName("description") val description: String,
    @SerializedName("time_seconds") val timeSeconds: Int,
    @SerializedName("is_passive") val isPassive: Boolean
)

data class ClassificationJsonModel(
    @SerializedName("course") val course: List<String>,
    @SerializedName("category") val category: List<String>,
    @SerializedName("meal_timing") val mealTiming: List<String>
)
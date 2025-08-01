package com.cryptic.roundrecipes.firsttime

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cryptic.roundrecipes.database.AppDatabase
import com.cryptic.roundrecipes.database.Classification
import com.cryptic.roundrecipes.database.CountryJsonModel
import com.cryptic.roundrecipes.database.Ingredient
import com.cryptic.roundrecipes.database.Instruction
import com.cryptic.roundrecipes.database.Recipe
import com.cryptic.roundrecipes.database.RecipeName
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.coroutineScope

private const val TAG = "PrepopulateWorker"

class PrepopulateWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            Log.d(TAG, "Starting database prepopulation...")

            // 1. Read the master JSON file from assets
            val jsonString = applicationContext.assets.open("finalCombined.json")
                .bufferedReader().use { it.readText() }

            // 2. Define the type for Gson (a map where key is country code, value is CountryJsonModel)
            val typeToken = object : TypeToken<Map<String, CountryJsonModel>>() {}.type
            val data: Map<String, CountryJsonModel> = Gson().fromJson(jsonString, typeToken)

            val database = AppDatabase.getInstance(applicationContext)
            val dao = database.recipeDao()

            var recipeCount = 0

            // 3. Loop through each country ("in", "cn", etc.)
            data.forEach { (countryCode, countryData) ->
                Log.d(TAG, "Processing country: $countryCode")

                // Loop through each recipe inside the country's "recipes" object
                countryData.recipes.forEach { (recipeId, recipeJson) ->

                    // 4. Map the JSON Model to our Room Database Entities

                    val localNameString = recipeJson.name.local?.let { localElement ->
                        when {
                            // Case 1: It's a string like "Biryani"
                            localElement.isJsonPrimitive && localElement.asJsonPrimitive.isString -> {
                                localElement.asString
                            }
                            // Case 2: It's an array like ["Biryani", "Biriyani"]
                            localElement.isJsonArray -> {
                                // Get the first element from the array, if it exists and is a string
                                localElement.asJsonArray.firstOrNull()?.asString
                            }
                            // Case 3: It's something else (null, a number, an object)
                            else -> null
                        }
                    }

                    val enNameString = recipeJson.name.en.let { enElement ->
                        when {
                            // Case 1: It's a simple string like "Biryani"
                            enElement.isJsonPrimitive && enElement.asJsonPrimitive.isString -> {
                                enElement.asString
                            }
                            // Case 2: It's an array like ["Biryani"]
                            enElement.isJsonArray -> {
                                // Safely get the first string from the array
                                enElement.asJsonArray.firstOrNull()?.asString
                            }
                            // Case 3: It's some other unexpected type (a number, an object).
                            // Return a default empty string to prevent a crash.
                            else -> ""
                        }
                    } ?: ""


                    val recipeEntity = Recipe(
                        recipeId = recipeId,
                        countryCode = countryCode,
                        name = RecipeName(
                            local = localNameString ?: enNameString,
                            en = enNameString
                        ),
                        isNonVeg = recipeJson.isNonVeg,
                        totalTimeSeconds = recipeJson.totalTimeSeconds,
                        warning = recipeJson.warning,
                        overloadInfo = recipeJson.overloadInfo, // This can be null
                        classification = Classification(
                            // Gracefully handle placeholder {} objects
                            course = recipeJson.classification.course ?: emptyList(),
                            category = recipeJson.classification.category ?: emptyList(),
                            mealTiming = recipeJson.classification.mealTiming ?: emptyList()
                        )
                    )

                    val ingredients = recipeJson.ingredients.map { ingredientJson ->
                        Ingredient(
                            recipeOwnerId = recipeId,
                            name = ingredientJson.name,
                            amount = ingredientJson.amount,
                            unit = ingredientJson.unit,
                            availability = ingredientJson.availability
                        )
                    }

                    val instructions = recipeJson.instructions.map { instructionJson ->
                        Instruction(
                            recipeOwnerId = recipeId,
                            step = instructionJson.step,
                            description = instructionJson.description,
                            timeSeconds = instructionJson.timeSeconds,
                            isPassive = instructionJson.isPassive
                        )
                    }

                    // 5. Insert the fully mapped recipe into the database using our transaction
                    dao.insertFullRecipe(recipeEntity, ingredients, instructions)
                    recipeCount++
                }
            }

            Log.i(TAG, "SUCCESS: Successfully prepopulated database with $recipeCount recipes.")
            Result.success()

        } catch (ex: Exception) {
            Log.e(TAG, "FAILURE: Error prepopulating database", ex)
            // If the process fails, we should not consider it a success.
            // Room's transactions will help ensure the DB isn't left in a half-finished state.
            Result.failure()
        }
    }
}
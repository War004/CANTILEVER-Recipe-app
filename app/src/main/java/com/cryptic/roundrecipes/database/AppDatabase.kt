package com.cryptic.roundrecipes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.cryptic.roundrecipes.firsttime.PrepopulateWorker

@Database(
    entities = [Recipe::class, Ingredient::class, Instruction::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_database"
                )
                    // This callback is triggered ONLY when the DB is created for the first time
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Use WorkManager to do the parsing in the background
                            val request = OneTimeWorkRequestBuilder<PrepopulateWorker>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
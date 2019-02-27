package ru.teamdroid.recipecraft.room

import android.content.Context

/**
 * Enables injection of data sources.
 */
object Injection {

    fun provideRecipesViewModelFactory(context: Context): ViewModelFactory {
        val dataSource = RecipecraftRoomDatabase.getInstance(context).recipesDao()
        return ViewModelFactory(dataSource)
    }

    fun provideIngredientsViewModelFactory(context: Context): ViewModelFactory {
        val dataSource = RecipecraftRoomDatabase.getInstance(context).ingredientsDao()
        return ViewModelFactory(dataSource)
    }

}

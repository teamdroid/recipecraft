package ru.teamdroid.recipecraft.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ru.teamdroid.recipecraft.data.model.Ingredients
import ru.teamdroid.recipecraft.data.model.RecipeIngredients
import ru.teamdroid.recipecraft.data.model.Recipes

@Database(entities = [(Ingredients::class), (Recipes::class), (RecipeIngredients::class)], version = 1, exportSchema = false)
abstract class RecipecraftDB : RoomDatabase() {
    abstract fun recipeDao(): RecipesDao
}
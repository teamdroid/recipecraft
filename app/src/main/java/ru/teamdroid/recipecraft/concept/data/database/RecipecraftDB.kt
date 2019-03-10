package ru.teamdroid.recipecraft.concept.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ru.teamdroid.recipecraft.concept.data.model.Ingredients
import ru.teamdroid.recipecraft.concept.data.model.RecipeIngredients
import ru.teamdroid.recipecraft.concept.data.model.Recipes

@Database(entities = [(Ingredients::class), (Recipes::class), (RecipeIngredients::class)], version = 1, exportSchema = false)
abstract class RecipecraftDB : RoomDatabase() {
    abstract fun recipeDao(): RecipesDao
}
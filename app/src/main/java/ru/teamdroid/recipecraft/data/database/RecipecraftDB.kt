package ru.teamdroid.recipecraft.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ru.teamdroid.recipecraft.data.model.IngredientEntity
import ru.teamdroid.recipecraft.data.model.RecipeEntity
import ru.teamdroid.recipecraft.data.model.RecipeIngredientsEntity

@Database(entities = [(IngredientEntity::class), (RecipeEntity::class), (RecipeIngredientsEntity::class)], version = 1, exportSchema = false)
abstract class RecipecraftDB : RoomDatabase() {
    abstract fun recipeDao(): RecipesDao
}
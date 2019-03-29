package ru.teamdroid.recipecraft.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.teamdroid.recipecraft.data.model.IngredientEntity
import ru.teamdroid.recipecraft.data.model.InstructionEntity
import ru.teamdroid.recipecraft.data.model.RecipeEntity
import ru.teamdroid.recipecraft.data.model.RecipeIngredientsEntity

@Database(entities = [(IngredientEntity::class), (RecipeEntity::class), (RecipeIngredientsEntity::class), (InstructionEntity::class)], version = 1, exportSchema = false)
abstract class RecipecraftDB : RoomDatabase() {
    abstract fun recipeDao(): RecipesDao
}
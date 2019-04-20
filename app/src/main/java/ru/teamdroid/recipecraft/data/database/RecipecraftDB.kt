package ru.teamdroid.recipecraft.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.teamdroid.recipecraft.data.database.entities.*

@Database(entities = [(IngredientEntity::class), (RecipeEntity::class), (RecipeIngredientsEntity::class), (InstructionEntity::class), (UnitMeasureEntity::class)], version = 2, exportSchema = false)
abstract class RecipecraftDB : RoomDatabase() {
    abstract fun recipeDao(): RecipesDao
}
package ru.teamdroid.recipecraft.room.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import ru.teamdroid.recipecraft.room.entity.Ingredients
import ru.teamdroid.recipecraft.room.entity.Recipe

@Dao
interface RecipesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): Flowable<MutableList<Recipe>>

    @Query("SELECT * FROM recipe WHERE recipe.isBookmarked == 1")
    fun getAllBookmarkedRecipes(): Flowable<MutableList<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(listRecipe:MutableList<Recipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredients(listIngredients:MutableList<Ingredients>)

    @Delete
    fun deleteRecipe(recipe: Recipe)

    @Update
    fun updateRecipe(recipe: Recipe)

}
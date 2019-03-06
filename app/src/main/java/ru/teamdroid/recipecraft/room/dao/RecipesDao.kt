package ru.teamdroid.recipecraft.room.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import ru.teamdroid.recipecraft.room.entity.Ingredients
import ru.teamdroid.recipecraft.room.entity.Recipes
import ru.teamdroid.recipecraft.room.entity.RecipeIngredients

@Dao
interface RecipesDao {

    @Query("SELECT * FROM Recipes")
    fun getAllRecipes(): Flowable<MutableList<Recipes>>

    @Query("SELECT * FROM recipes WHERE recipes.isBookmarked == 1")
    fun getAllBookmarkedRecipes(): Flowable<MutableList<Recipes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(listRecipes: MutableList<Recipes>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredients(listIngredients: MutableList<Ingredients>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeIngredients(listRecipeIngredients: MutableList<RecipeIngredients>)

    @Delete
    fun deleteRecipe(recipes: Recipes)

    @Update
    fun updateRecipe(recipes: Recipes)

}
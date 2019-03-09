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

    @Query("SELECT recipes.idRecipe, recipe_ingredients.id, recipe_ingredients.idIngredient, recipes.time, recipes.portion, ingredients.title, recipe_ingredients.amount FROM recipes " +
            "LEFT JOIN recipe_ingredients ON recipes.idRecipe = recipe_ingredients.idRecipe " +
            "LEFT JOIN ingredients ON recipe_ingredients.idIngredient = ingredients.idIngredient")
    fun getAllIngredientsById(): Flowable<MutableList<RecipeIngredients>>

}
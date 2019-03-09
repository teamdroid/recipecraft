package ru.teamdroid.recipecraft.room.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import ru.teamdroid.recipecraft.room.entity.Ingredients
import ru.teamdroid.recipecraft.room.entity.RecipeIngredients

@Dao
interface IngredientsDao {

    @Query("SELECT * FROM ingredients")
    fun getAllIngredients(): Flowable<MutableList<Ingredients>>

    @Query("SELECT * FROM recipe_ingredients")
    fun getAllRecipeIngredients(): Flowable<MutableList<RecipeIngredients>>

    @Query("SELECT recipes.idRecipe, recipe_ingredients.id, recipe_ingredients.idIngredient, recipes.time, recipes.portion, ingredients.title, recipe_ingredients.amount FROM recipes " +
            "LEFT JOIN recipe_ingredients ON recipes.idRecipe = recipe_ingredients.idRecipe " +
            "LEFT JOIN ingredients ON recipe_ingredients.idIngredient = ingredients.idIngredient")
    fun getAllIngredientsById(): Flowable<MutableList<RecipeIngredients>>

    @Query("DELETE FROM ingredients")
    fun clearAllIngredients()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredients(ingredients: Ingredients)

    @Update
    fun updateIngredients(ingredients: Ingredients)

    @Delete
    fun deleteIngredients(item: Ingredients)
}
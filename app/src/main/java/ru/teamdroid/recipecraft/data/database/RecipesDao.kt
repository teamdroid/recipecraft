package ru.teamdroid.recipecraft.data.database

import android.arch.persistence.room.*
import io.reactivex.Flowable
import ru.teamdroid.recipecraft.data.model.Ingredients
import ru.teamdroid.recipecraft.data.model.RecipeIngredients
import ru.teamdroid.recipecraft.data.model.Recipes

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
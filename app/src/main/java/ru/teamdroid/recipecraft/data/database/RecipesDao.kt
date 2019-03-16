package ru.teamdroid.recipecraft.data.database

import android.arch.persistence.room.*
import io.reactivex.Flowable
import ru.teamdroid.recipecraft.data.model.IngredientEntity
import ru.teamdroid.recipecraft.data.model.RecipeEntity
import ru.teamdroid.recipecraft.data.model.RecipeIngredientsEntity

@Dao
interface RecipesDao {

    @Query("SELECT * FROM Recipe")
    fun getAllRecipes(): Flowable<MutableList<RecipeEntity>>

    @Query("SELECT * FROM recipe WHERE recipe.isBookmarked == 1")
    fun getAllBookmarkedRecipes(): Flowable<MutableList<RecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(listRecipes: MutableList<RecipeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredients(listIngredients: MutableList<IngredientEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeIngredients(listRecipeIngredients: MutableList<RecipeIngredientsEntity>)

    @Delete
    fun deleteRecipe(recipes: RecipeEntity)

    @Update
    fun updateRecipe(recipes: RecipeEntity)

    @Query("SELECT * FROM recipe INNER JOIN recipe_ingredients ON recipe.idRecipe = recipe_ingredients.idRecipe")
    fun getAllRecipeIngredients(): List<IngredientEntity>

}
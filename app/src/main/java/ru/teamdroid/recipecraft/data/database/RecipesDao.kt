package ru.teamdroid.recipecraft.data.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.teamdroid.recipecraft.data.database.entities.IngredientEntity
import ru.teamdroid.recipecraft.data.database.entities.InstructionEntity
import ru.teamdroid.recipecraft.data.database.entities.RecipeEntity
import ru.teamdroid.recipecraft.data.database.entities.RecipeIngredientsEntity

@Dao
interface RecipesDao {

    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): Flowable<MutableList<RecipeEntity>>

    @Query("SELECT * FROM recipe WHERE idRecipe IN (:listRecipesIds)")
    fun getRecipesByIds(listRecipesIds: List<Int>): Flowable<MutableList<RecipeEntity>>

    @Query("SELECT * FROM recipe WHERE isBookmarked = 1")
    fun getAllBookmarkedRecipes(): Flowable<MutableList<RecipeEntity>>

    @Update
    fun bookmark(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(listRecipes: MutableList<RecipeEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredients(listIngredients: MutableList<IngredientEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeIngredients(listRecipeIngredients: MutableList<RecipeIngredientsEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeInstructions(listRecipeInstructions: MutableList<InstructionEntity>): Completable

    @Delete
    fun deleteRecipe(recipes: RecipeEntity)

    @Query("SELECT recipe.idRecipe FROM (" +
            "  SELECT recipe.idRecipe FROM recipe" +
            "    JOIN recipe_ingredients ON recipe.idRecipe = recipe_ingredients.idRecipe" +
            "    JOIN ingredient ON ingredient.idIngredient = recipe_ingredients.idIngredient" +
            "    GROUP BY recipe.idRecipe" +
            "    HAVING sum(CASE WHEN ingredient.title in (:listIngredients) THEN 1 ELSE 0 END) = :count" +
            ") t JOIN recipe ON recipe.idRecipe = t.idRecipe")
    fun findRecipeByIngredients(listIngredients: List<String>, count: Int): Single<List<Int>>

    @Update
    fun updateRecipe(recipes: RecipeEntity)

    @Query("SELECT title FROM ingredient")
    fun loadIngredientsTitle(): Single<List<String>>

    @Query("SELECT recipe_ingredients.idIngredient, ingredient.title FROM recipe " +
            "LEFT JOIN recipe_ingredients ON recipe.idRecipe = recipe_ingredients.idRecipe " +
            "LEFT JOIN ingredient ON recipe_ingredients.idIngredient = ingredient.idIngredient WHERE recipe_ingredients.idRecipe = :idRecipe")
    fun getAllRecipeIngredientsById(idRecipe: Int): Single<MutableList<IngredientEntity>>

    @Query("SELECT * FROM instruction WHERE idRecipe = :idRecipe")
    fun getInstructionsById(idRecipe: Int): Single<MutableList<InstructionEntity>>

}
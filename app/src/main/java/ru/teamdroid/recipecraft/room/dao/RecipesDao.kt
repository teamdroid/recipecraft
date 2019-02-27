package ru.teamdroid.recipecraft.room.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import ru.teamdroid.recipecraft.room.entity.Recipe

@Dao
interface RecipesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): Flowable<MutableList<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(listRecipe:MutableList<Recipe>)

    @Delete
    fun deleteRecipe(recipe: Recipe)

    @Update
    fun updateRecipe(recipe: Recipe)

}
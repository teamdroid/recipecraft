package ru.teamdroid.recipecraft.room.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import ru.teamdroid.recipecraft.room.entity.Ingredients

@Dao
interface IngredientsDao {
    @Query("SELECT * FROM ingredients")
    fun getAllIngredients(): Flowable<MutableList<Ingredients>>

    @Query("SELECT * FROM ingredients WHERE id = :id")
    fun getByIdIngredients(id: Int): Single<Ingredients>

    @Query("DELETE FROM ingredients")
    fun clearAllIngredients()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredients(ingredients: Ingredients)

    @Update
    fun updateIngredients(ingredients: Ingredients)

    @Delete
    fun deleteIngredients(item: Ingredients)
}
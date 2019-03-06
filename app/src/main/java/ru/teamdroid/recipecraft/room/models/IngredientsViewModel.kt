package ru.teamdroid.recipecraft.room.models

import android.arch.lifecycle.ViewModel
import io.reactivex.Single
import ru.teamdroid.recipecraft.room.dao.IngredientsDao
import ru.teamdroid.recipecraft.room.entity.RecipeIngredients

class IngredientsViewModel(private val dataSource: IngredientsDao) : ViewModel() {

    fun getAllRecipeIngredients(): Single<MutableList<RecipeIngredients>> {
        return dataSource.getAllIngredientsById()
    }
}

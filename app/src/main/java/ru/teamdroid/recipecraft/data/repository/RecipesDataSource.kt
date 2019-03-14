package ru.teamdroid.recipecraft.data.repository

import io.reactivex.Flowable
import ru.teamdroid.recipecraft.data.model.Recipes

interface RecipesDataSource {
    fun loadRecipe(forceRemote: Boolean): Flowable<List<Recipes>>
    fun addRecipe(recipes: Recipes)
    fun clearData()
}
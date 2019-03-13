package ru.teamdroid.recipecraft.repository

import io.reactivex.Flowable
import ru.teamdroid.recipecraft.data.model.Recipes

interface RecipesDataSource {
    fun loadRecipe(forceRemote: Boolean): Flowable<MutableList<Recipes>>
    fun clearData()
}
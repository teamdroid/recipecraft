package ru.teamdroid.recipecraft.concept.repository

import io.reactivex.Flowable
import ru.teamdroid.recipecraft.concept.data.model.Recipes

interface RecipesDataSource {
    fun loadRecipe(forceRemote: Boolean): Flowable<MutableList<Recipes>>
    fun clearData()
}
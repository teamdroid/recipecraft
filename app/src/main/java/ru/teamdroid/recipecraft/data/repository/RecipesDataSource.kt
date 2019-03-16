package ru.teamdroid.recipecraft.data.repository

import io.reactivex.Flowable
import ru.teamdroid.recipecraft.data.model.Ingredient
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.data.model.RecipeIngredients

interface RecipesDataSource {
    fun loadLocalRecipe(forceRemote: Boolean): Flowable<MutableList<Recipe>>
    fun loadRemoteRecipe(forceRemote: Boolean): Flowable<MutableList<Recipe>>
    fun addRecipe(recipes: Recipe)
    fun addIngredients(listIngredients: MutableList<Ingredient>)
    fun addRecipeIngredients(listRecipeIngredients: MutableList<RecipeIngredients>)
    fun clearData()
}
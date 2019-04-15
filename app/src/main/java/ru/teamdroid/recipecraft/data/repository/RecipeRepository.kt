package ru.teamdroid.recipecraft.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.teamdroid.recipecraft.data.api.ReportMessage
import ru.teamdroid.recipecraft.data.api.Response
import ru.teamdroid.recipecraft.data.model.Recipe
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val recipeDataSource: RecipesDataSource) {

    fun loadRecipes(forceRemote: Boolean): Flowable<MutableList<Recipe>> {
        return if (forceRemote) {
            loadRemoteRecipes()
        } else {
            recipeDataSource.loadLocalRecipes()
        }
    }

    private fun loadRemoteRecipes(): Flowable<MutableList<Recipe>> {
        return recipeDataSource.loadRemoteRecipes().switchMap {
            recipeDataSource.addRecipes(it).andThen(recipeDataSource.loadLocalRecipes())
        }
    }

    fun loadBookmarkedRecipes(): Flowable<MutableList<Recipe>> = recipeDataSource.loadBookmarkRecipes()

    fun loadIngredientsTitle(): Single<List<String>> = recipeDataSource.loadIngredientsTitle()

    fun findRecipeByIngredients(listIngredients: List<String>, count: Int): Flowable<MutableList<Recipe>> = recipeDataSource.findRecipesByIngredients(listIngredients, count)

    fun bookmark(recipe: Recipe): Completable = recipeDataSource.bookmark(recipe)

    fun sendReportMessage(reportMessage: ReportMessage): Single<Response> = recipeDataSource.sendReportMessage(reportMessage)

}
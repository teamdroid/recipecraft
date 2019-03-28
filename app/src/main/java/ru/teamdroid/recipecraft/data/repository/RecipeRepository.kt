package ru.teamdroid.recipecraft.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.teamdroid.recipecraft.data.api.ReportMessage
import ru.teamdroid.recipecraft.data.api.Response
import ru.teamdroid.recipecraft.data.model.Recipe
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val recipeDataSource: RecipesDataSource) {

    fun loadRecipe(forceRemote: Boolean): Flowable<MutableList<Recipe>> {
        return if (forceRemote) {
            loadRemoteData()
        } else {
            recipeDataSource.loadLocalRecipe()
        }
    }

    fun loadBookmarkedRecipe(): Flowable<MutableList<Recipe>> {
        return recipeDataSource.loadBookmarkRecipes()
    }

    private fun loadRemoteData(): Flowable<MutableList<Recipe>> {
        return recipeDataSource.loadRemoteRecipe().switchMap {
            recipeDataSource.addRecipes(it).andThen(recipeDataSource.loadLocalRecipe())
        }
    }

    fun bookmark(recipe: Recipe): Completable = recipeDataSource.bookmark(recipe)

    fun sendReportMessage(reportMessage: ReportMessage): Single<Response> {
        return recipeDataSource.sendReportMessage(reportMessage)
    }

}
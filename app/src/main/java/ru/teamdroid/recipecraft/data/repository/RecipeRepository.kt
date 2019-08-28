package ru.teamdroid.recipecraft.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.teamdroid.recipecraft.data.Config
import ru.teamdroid.recipecraft.data.api.FeedbackMessage
import ru.teamdroid.recipecraft.data.api.Response
import ru.teamdroid.recipecraft.data.model.Recipe
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val recipeDataSource: RecipesDataSource) {

    fun loadRecipes(forceRemote: Boolean, sortType: String, offset: Int): Flowable<MutableList<Recipe>> {
        return if (forceRemote) {
            loadRemoteRecipes(sortType)
        } else {
            recipeDataSource.loadLocalRecipes(sortType, offset)
        }
    }

    fun getRecipesCount(): Single<Int> {
        return recipeDataSource.getRecipesCount()
    }

    private fun loadRemoteRecipes(sortType : String): Flowable<MutableList<Recipe>> {
        return recipeDataSource.loadRemoteRecipes().switchMap {
            recipeDataSource.addRecipes(it).andThen(recipeDataSource.loadLocalRecipes(sortType, Config.LIMIT_RECIPES))
        }
    }

    fun loadBookmarkedRecipes(): Flowable<MutableList<Recipe>> = recipeDataSource.loadBookmarkRecipes()

    fun loadIngredientsTitle(): Single<List<String>> = recipeDataSource.loadIngredientsTitle()

    fun findRecipeByIngredients(listIngredients: List<String>, count: Int): Flowable<MutableList<Recipe>> = recipeDataSource.findRecipesByIngredients(listIngredients, count)

    fun bookmark(recipe: Recipe): Completable {
        recipe.isBookmarked = !recipe.isBookmarked
        return recipeDataSource.bookmark(recipe)
    }

    fun sendReportMessage(feedbackMessage: FeedbackMessage): Single<Response> = recipeDataSource.sendReportMessage(feedbackMessage)

}
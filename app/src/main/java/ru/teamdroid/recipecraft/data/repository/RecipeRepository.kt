package ru.teamdroid.recipecraft.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.teamdroid.recipecraft.data.model.Recipe
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val recipeDataSource: RecipesDataSource) {

    fun loadRecipe(forceRemote: Boolean): Flowable<MutableList<Recipe>> {
        return if (forceRemote) {
            loadRemoteData()
        } else {
            recipeDataSource.loadLocalRecipe(false).take(1).filter { !it.isNullOrEmpty() }.switchIfEmpty(loadRemoteData())
        }
    }

    private fun loadRemoteData(): Flowable<MutableList<Recipe>> {
        return recipeDataSource.loadRemoteRecipe().switchMap {
            recipeDataSource.addRecipes(it).andThen(recipeDataSource.loadLocalRecipe(false))
        }
    }

    fun bookmark(recipe: Recipe): Completable {
        recipe.isBookmarked = !recipe.isBookmarked
        return recipeDataSource.bookmark(recipe)
    }

}
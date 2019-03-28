package ru.teamdroid.recipecraft.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.ui.MainActivity
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val recipeDataSource: RecipesDataSource) {

    fun loadRecipe(forceRemote: Boolean): Flowable<MutableList<Recipe>> {
        return if (forceRemote) {
            loadRemoteData()
        } else {
            if (MainActivity.isFirstStartup) loadRemoteData() else recipeDataSource.loadLocalRecipe() // TODO: FIX IT 'CAUSE IT'S HACK
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

}
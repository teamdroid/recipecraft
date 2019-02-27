package ru.teamdroid.recipecraft.room.models

import android.arch.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import ru.teamdroid.recipecraft.room.dao.RecipesDao
import ru.teamdroid.recipecraft.room.entity.Recipe

class RecipesViewModel(private val dataSource: RecipesDao) : ViewModel() {

    fun insertRecipe(recipe: Recipe): Completable {
        return Completable.fromAction {
            dataSource.insertRecipe(recipe)
        }
    }

    fun getAllBookmarkedRecipes(): Flowable<MutableList<Recipe>> {
        return dataSource.getAllBookmarkedRecipes()
    }


    fun insertRecipes(listRecipe : MutableList<Recipe>) : Completable {
        return Completable.fromAction {
            dataSource.insertRecipes(listRecipe)
        }
    }

    fun bookmarkRecipe(recipe: Recipe) : Completable {
        recipe.isBookmarked = true
        return Completable.fromAction {
            dataSource.updateRecipe(recipe)
        }
    }

    fun unbookmarkRecipe(recipe: Recipe) : Completable {
        recipe.isBookmarked = false
        return Completable.fromAction {
            dataSource.updateRecipe(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe): Completable {
        return Completable.fromAction {
            dataSource.deleteRecipe(recipe)
        }
    }

    fun getAllRecipes(): Flowable<MutableList<Recipe>> {
        return dataSource.getAllRecipes()
    }
}
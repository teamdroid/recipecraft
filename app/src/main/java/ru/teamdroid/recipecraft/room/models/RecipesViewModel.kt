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

    fun insertRecipes(listRecipe : MutableList<Recipe>) : Completable {
        return Completable.fromAction {
            dataSource.insertRecipes(listRecipe)
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

    fun update(recipe: Recipe): Completable {

        if (recipe.isBookmarked)
            return deleteRecipe(recipe)
        else {
            recipe.isBookmarked = true
            recipe.title = (Math.random()*10).toString()
        }


        return Completable.fromAction {
            dataSource.updateRecipe(recipe)
        }
    }
}
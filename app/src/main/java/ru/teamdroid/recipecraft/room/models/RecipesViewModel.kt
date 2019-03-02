package ru.teamdroid.recipecraft.room.models

import android.arch.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.teamdroid.recipecraft.room.dao.RecipesDao
import ru.teamdroid.recipecraft.room.entity.Ingredients
import ru.teamdroid.recipecraft.room.entity.Recipe

class RecipesViewModel(private val dataSource: RecipesDao) : ViewModel() {

    fun getAllBookmarkedRecipes(): Flowable<MutableList<Recipe>> {
        return dataSource.getAllBookmarkedRecipes()
    }


    fun insertRecipes(listRecipe : MutableList<Recipe>) : Single<Recipe> {
        return Single.fromObservable {
            dataSource.insertRecipes(listRecipe)
        }
    }

    fun insertIngredients(listIngredients : MutableList<Ingredients>) : Single<Ingredients> {
        return Single.fromObservable {
            dataSource.insertIngredients(listIngredients)
        }
    }

    fun bookmarkRecipe(recipe: Recipe) : Completable {
        recipe.isBookmarked = !recipe.isBookmarked
        return Completable.fromAction {
            dataSource.updateRecipe(recipe)
        }
    }

    fun getAllRecipes(): Flowable<MutableList<Recipe>> {
        return dataSource.getAllRecipes()
    }
}
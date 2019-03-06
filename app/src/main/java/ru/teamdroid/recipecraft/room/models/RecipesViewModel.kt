package ru.teamdroid.recipecraft.room.models

import android.arch.lifecycle.ViewModel

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.teamdroid.recipecraft.room.dao.RecipesDao
import ru.teamdroid.recipecraft.room.entity.Ingredients
import ru.teamdroid.recipecraft.room.entity.Recipes
import ru.teamdroid.recipecraft.room.entity.RecipeIngredients

class RecipesViewModel(private val dataSource: RecipesDao) : ViewModel() {

    fun getAllBookmarkedRecipes(): Flowable<MutableList<Recipes>> {
        return dataSource.getAllBookmarkedRecipes()
    }

    fun insertRecipes(listRecipes: MutableList<Recipes>): Completable {
        return Completable.fromAction {
            dataSource.insertRecipes(listRecipes)
        }
    }

    fun insertIngredients(listIngredients: MutableList<Ingredients>): Completable {
        return Completable.fromAction {
            dataSource.insertIngredients(listIngredients)
        }
    }

    fun insertRecipeIngredients(listRecipeIngredients: MutableList<RecipeIngredients>): Completable {
        return Completable.fromAction {
            dataSource.insertRecipeIngredients(listRecipeIngredients)
        }
    }

    fun bookmarkRecipe(recipes: Recipes): Completable {
        recipes.isBookmarked = !recipes.isBookmarked
        return Completable.fromAction {
            dataSource.updateRecipe(recipes)
        }
    }

    fun getAllRecipes(): Flowable<MutableList<Recipes>> {
        return dataSource.getAllRecipes()
    }
}
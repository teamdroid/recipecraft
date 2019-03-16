package ru.teamdroid.recipecraft.data.repository

import io.reactivex.Flowable
import ru.teamdroid.recipecraft.data.model.Ingredient
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.data.model.RecipeIngredients
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val localDataSource: RecipesDataSource, private val remoteDataSource: RecipesDataSource) {


    fun loadRecipe(forceRemote: Boolean): Flowable<MutableList<Recipe>> {
        return if (true) {
            loadRemoteData()
        } else {
            localDataSource.loadLocalRecipe(false).filter { !it.isEmpty() }.switchIfEmpty(loadRemoteData())
        }
    }

    private fun loadRemoteData(): Flowable<MutableList<Recipe>> {

        val listRecipes: MutableList<Recipe> = arrayListOf()
        val listRecipeIngredients: MutableList<RecipeIngredients> = arrayListOf()
        val listIngredients: MutableList<Ingredient> = arrayListOf()

        return remoteDataSource.loadRemoteRecipe(true)

//
//        listRecipes.add(recipe)
//
//        recipe.ingredients.forEach {
//            listIngredients.add(Ingredient(it.idIngredient, it.title, it.amount))
//            listRecipeIngredients.add(RecipeIngredients(id = it.id, idRecipe = recipe.idRecipe, idIngredient = it.idIngredient))
//        }
//
//
//
//        with(localDataSource) {
//            addRecipe(recipe)
//            addIngredients(listIngredients)
//            addRecipeIngredients(listRecipeIngredients)
//        }


    }
}
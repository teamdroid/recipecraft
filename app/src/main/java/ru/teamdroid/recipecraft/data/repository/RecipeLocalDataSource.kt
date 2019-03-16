package ru.teamdroid.recipecraft.data.repository

import io.reactivex.Flowable
import ru.teamdroid.recipecraft.data.api.RecipeService
import ru.teamdroid.recipecraft.data.database.RecipesDao
import ru.teamdroid.recipecraft.data.model.Ingredient
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.data.model.RecipeDetailEntityToRecipe
import ru.teamdroid.recipecraft.data.model.RecipeIngredients
import javax.inject.Inject

class RecipeLocalDataSource @Inject constructor(private val recipeDao: RecipesDao, private val recipeService: RecipeService) : RecipesDataSource {

    private val mapper: RecipeDetailEntityToRecipe = RecipeDetailEntityToRecipe() // TODO : REMOVE

    override fun addRecipe(recipes: Recipe) {}

    override fun addIngredients(listIngredients: MutableList<Ingredient>) {
        recipeDao.insertIngredients(mapper.mapIngredients(listIngredients))
    }


    override fun addRecipeIngredients(listRecipeIngredients: MutableList<RecipeIngredients>) {

    }

    override fun loadLocalRecipe(forceRemote: Boolean): Flowable<MutableList<Recipe>> {
        return recipeDao.getAllRecipes().map { mapper.mapDetailRecipe(it, recipeDao.getAllRecipeIngredients()) }
    }

    override fun clearData() {
        //recipeDao.deleteRecipes()
    }

    override fun loadRemoteRecipe(forceRemote: Boolean): Flowable<MutableList<Recipe>> {
        return recipeService.getAllRecipes("ru")
    }

}
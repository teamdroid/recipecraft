package ru.teamdroid.recipecraft.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.teamdroid.recipecraft.data.api.RecipeService
import ru.teamdroid.recipecraft.data.database.RecipesDao
import ru.teamdroid.recipecraft.data.model.*
import javax.inject.Inject

class RecipeDataSourceImpl @Inject constructor(private val recipeDao: RecipesDao, private val recipeService: RecipeService) : RecipesDataSource {


    private val mapper: RecipeDetailEntityToRecipe = RecipeDetailEntityToRecipe() // TODO : REMOVE

    override fun addRecipe(recipes: Recipe) {
        recipeDao.insertRecipe(RecipeEntity(idRecipe = recipes.idRecipe, title = recipes.title))
    }

    override fun addRecipes(recipes: MutableList<Recipe>): Completable {
        return recipeDao.insertRecipes(mapper.mapRecipe(recipes))
    }

    override fun addIngredients(listIngredients: MutableList<Ingredient>) {
        return recipeDao.insertIngredients(mapper.mapIngredients(listIngredients))
    }

    override fun addRecipeIngredients(listRecipeIngredients: MutableList<RecipeIngredients>) {
        return recipeDao.insertRecipeIngredients(mapper.mapRecipeIngredients(listRecipeIngredients))
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
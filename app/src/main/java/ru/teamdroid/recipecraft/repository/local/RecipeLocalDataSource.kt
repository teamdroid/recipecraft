package ru.teamdroid.recipecraft.repository.local

import io.reactivex.Flowable
import ru.teamdroid.recipecraft.data.database.RecipesDao
import ru.teamdroid.recipecraft.data.model.Recipes
import ru.teamdroid.recipecraft.repository.RecipesDataSource
import javax.inject.Inject

class RecipeLocalDataSource @Inject constructor(private val recipeDao: RecipesDao) : RecipesDataSource {

    override fun addRecipe(recipes: Recipes) {
        recipeDao.insertRecipe(recipes)
    }

    override fun loadRecipe(forceRemote: Boolean): Flowable<List<Recipes>> {
        return recipeDao.getAllRecipes()
    }

    override fun clearData() {
        //recipeDao.deleteRecipes()
    }

}
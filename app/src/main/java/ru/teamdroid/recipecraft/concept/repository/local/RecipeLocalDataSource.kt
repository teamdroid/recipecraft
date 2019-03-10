package ru.teamdroid.recipecraft.concept.repository.local

import io.reactivex.Flowable
import ru.teamdroid.recipecraft.concept.data.database.RecipesDao
import ru.teamdroid.recipecraft.concept.data.model.Recipes
import ru.teamdroid.recipecraft.concept.repository.RecipesDataSource
import javax.inject.Inject

class RecipeLocalDataSource @Inject constructor(private val recipeDao: RecipesDao) : RecipesDataSource {

    override fun loadRecipe(forceRemote: Boolean): Flowable<MutableList<Recipes>> {
        return recipeDao.getAllRecipes()
    }

    override fun clearData() {
        //Currently, we do not need this for remote source.
        throw UnsupportedOperationException("Unsupported operation")
    }

}
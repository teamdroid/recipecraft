package ru.teamdroid.recipecraft.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.teamdroid.recipecraft.data.api.RecipeService
import ru.teamdroid.recipecraft.data.base.RecipeMapper
import ru.teamdroid.recipecraft.data.database.RecipesDao
import ru.teamdroid.recipecraft.data.model.Ingredient
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.data.model.RecipeEntity
import ru.teamdroid.recipecraft.data.model.RecipeIngredients
import javax.inject.Inject

class RecipeDataSourceImpl @Inject constructor(private val recipeDao: RecipesDao, private val recipeService: RecipeService) : RecipesDataSource {

    private val mapper: RecipeMapper = RecipeMapper() // TODO : INJECT IT

    override fun addRecipe(recipes: Recipe) {
        recipeDao.insertRecipe(RecipeEntity(idRecipe = recipes.idRecipe, title = recipes.title))
    }

    override fun addRecipes(recipes: MutableList<Recipe>): Completable {
        return recipeDao.insertRecipes(mapper.mapRecipe(recipes)).andThen(addIngredients(recipes)).andThen(addRecipeIngredients(recipes))
    }

    override fun addIngredients(recipes: MutableList<Recipe>): Completable {
        val listIngredients: MutableList<Ingredient> = arrayListOf()

        recipes.forEach { recipe ->
            recipe.ingredients.forEach {
                listIngredients.add(Ingredient(it.idIngredient, it.title, it.amount))
            }
        }

        return recipeDao.insertIngredients(mapper.mapIngredients(listIngredients))
    }

    override fun addRecipeIngredients(recipes: MutableList<Recipe>): Completable {
        val listRecipeIngredients: MutableList<RecipeIngredients> = arrayListOf()

        recipes.forEach { recipe ->
            recipe.ingredients.forEach {
                listRecipeIngredients.add(RecipeIngredients(id = it.id, idRecipe = recipe.idRecipe, idIngredient = it.idIngredient))
            }
        }

        return recipeDao.insertRecipeIngredients(mapper.mapRecipeIngredients(listRecipeIngredients))
    }

    override fun loadLocalRecipe(): Flowable<MutableList<Recipe>> {
        return recipeDao.getAllRecipes()
                .flatMap {
                    Flowable.fromIterable(it).flatMap { item ->
                        recipeDao.getAllRecipeIngredientsById(item.idRecipe)
                                .map { details ->
                                    mapper.mapDetailRecipe(item, details)
                                }.toFlowable()
                    }.toList().toFlowable()
                }
    }

    override fun loadBookmarkRecipes(): Flowable<MutableList<Recipe>> {
        return recipeDao.getAllBookmarkedRecipes()
                .flatMap {
                    Flowable.fromIterable(it).flatMap { item ->
                        recipeDao.getAllRecipeIngredientsById(item.idRecipe)
                                .map { details ->
                                    mapper.mapDetailRecipe(item, details)
                                }.toFlowable()
                    }.toList().toFlowable()
                }
    }

    override fun bookmark(recipe: Recipe): Completable {
        return Completable.fromAction {
            recipeDao.bookmark(mapper.map(recipe))
        }
    }

    override fun loadRemoteRecipe(): Flowable<MutableList<Recipe>> {
        return recipeService.getAllRecipes("ru")
    }

}
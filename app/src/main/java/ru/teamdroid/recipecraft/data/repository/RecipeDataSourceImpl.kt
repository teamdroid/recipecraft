package ru.teamdroid.recipecraft.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.teamdroid.recipecraft.data.api.RecipeService
import ru.teamdroid.recipecraft.data.api.ReportMessage
import ru.teamdroid.recipecraft.data.api.Response
import ru.teamdroid.recipecraft.data.base.RecipeMapper
import ru.teamdroid.recipecraft.data.database.RecipesDao
import ru.teamdroid.recipecraft.data.model.Ingredient
import ru.teamdroid.recipecraft.data.model.Instruction
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.data.model.RecipeIngredients
import javax.inject.Inject

class RecipeDataSourceImpl @Inject constructor(private val recipeDao: RecipesDao, private val recipeService: RecipeService) : RecipesDataSource {

    private val mapper: RecipeMapper = RecipeMapper() // TODO : INJECT IT

    override fun addRecipes(recipes: MutableList<Recipe>): Completable {

        return recipeDao.insertRecipes(mapper.mapRecipe(recipes))
                .andThen(addIngredients(recipes))
                .andThen(addRecipeIngredients(recipes))
                .andThen(addInstructions(recipes))
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

    override fun addInstructions(recipes: MutableList<Recipe>): Completable {
        val listRecipeInstructions: MutableList<Instruction> = arrayListOf()

        recipes.forEach { recipe ->
            recipe.insctructions.forEach {
                listRecipeInstructions.add(Instruction(idInstruction = it.idInstruction, idRecipe = recipe.idRecipe, title = it.title))
            }
        }

        return recipeDao.insertRecipeInstructions(mapper.mapRecipeInstructions(listRecipeInstructions))
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

    override fun sendReportMessage(reportMessage: ReportMessage): Single<Response> {
        return recipeService.sendReportMessage(reportMessage)
    }

    override fun getInstructionsById(idRecipe: Int): Single<MutableList<Instruction>> {
        return recipeDao.getInstructionsById(idRecipe).map { mapper.reverseInstructions(it) }
    }
}
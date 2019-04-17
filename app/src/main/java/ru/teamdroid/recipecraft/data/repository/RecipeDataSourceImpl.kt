package ru.teamdroid.recipecraft.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import ru.teamdroid.recipecraft.data.api.RecipeService
import ru.teamdroid.recipecraft.data.api.ReportMessage
import ru.teamdroid.recipecraft.data.api.Response
import ru.teamdroid.recipecraft.data.base.RecipeMapper
import ru.teamdroid.recipecraft.data.database.RecipesDao
import ru.teamdroid.recipecraft.data.database.entities.IngredientEntity
import ru.teamdroid.recipecraft.data.database.entities.InstructionEntity
import ru.teamdroid.recipecraft.data.model.Ingredient
import ru.teamdroid.recipecraft.data.model.Instruction
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.data.model.RecipeIngredients
import javax.inject.Inject

class RecipeDataSourceImpl @Inject constructor(private val recipeDao: RecipesDao,
                                               private val recipeService: RecipeService,
                                               private val recipeMapper: RecipeMapper) : RecipesDataSource {

    override fun addRecipes(recipes: MutableList<Recipe>): Completable {
        return recipeDao.insertRecipes(recipeMapper.mapRecipe(recipes))
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

        return recipeDao.insertIngredients(recipeMapper.mapIngredients(listIngredients))
    }

    override fun addRecipeIngredients(recipes: MutableList<Recipe>): Completable {
        val listRecipeIngredients: MutableList<RecipeIngredients> = arrayListOf()

        recipes.forEach { recipe ->
            recipe.ingredients.forEach {
                listRecipeIngredients.add(RecipeIngredients(id = it.id, idRecipe = recipe.idRecipe, idIngredient = it.idIngredient))
            }
        }

        return recipeDao.insertRecipeIngredients(recipeMapper.mapRecipeIngredients(listRecipeIngredients))
    }

    override fun addInstructions(recipes: MutableList<Recipe>): Completable {
        val listRecipeInstructions: MutableList<Instruction> = arrayListOf()

        recipes.forEach { recipe ->
            recipe.insctructions.forEach {
                listRecipeInstructions.add(Instruction(idInstruction = it.idInstruction, idRecipe = recipe.idRecipe, title = it.title))
            }
        }

        return recipeDao.insertRecipeInstructions(recipeMapper.mapRecipeInstructions(listRecipeInstructions))
    }

    override fun loadLocalRecipes(): Flowable<MutableList<Recipe>> {
        return recipeDao.getAllRecipes()
                .flatMap {
                    Flowable.fromIterable(it).flatMapSingle { recipe ->
                        recipeDao.getAllRecipeIngredientsById(recipe.idRecipe)
                                .zipWith(recipeDao.getInstructionsById(recipe.idRecipe),
                                        BiFunction<MutableList<IngredientEntity>, MutableList<InstructionEntity>, Recipe> { listIngredients, listInstruction ->
                                            recipeMapper.mapDetailRecipe(recipe, listIngredients, listInstruction)
                                        })
                    }.toList().toFlowable()
                }
    }

    override fun loadBookmarkRecipes(): Flowable<MutableList<Recipe>> {
        return recipeDao.getAllBookmarkedRecipes()
                .flatMap {
                    Flowable.fromIterable(it).flatMapSingle { recipe ->
                        recipeDao.getAllRecipeIngredientsById(recipe.idRecipe)
                                .zipWith(recipeDao.getInstructionsById(recipe.idRecipe),
                                        BiFunction<MutableList<IngredientEntity>, MutableList<InstructionEntity>, Recipe> { listIngredients, listInstruction ->
                                            recipeMapper.mapDetailRecipe(recipe, listIngredients, listInstruction)
                                        })
                    }.toList().toFlowable()
                }
    }

    override fun bookmark(recipe: Recipe): Completable {
        return Completable.fromAction {
            recipeDao.bookmark(recipeMapper.map(recipe))
        }
    }

    override fun findRecipesByIngredients(listIngredients: List<String>, count: Int): Flowable<MutableList<Recipe>> {
        return recipeDao.findRecipeByIngredients(listIngredients, count).flatMapPublisher { list ->
            recipeDao.getRecipesByIds(list).flatMap {
                Flowable.fromIterable(it).flatMapSingle { recipe ->
                    recipeDao.getAllRecipeIngredientsById(recipe.idRecipe)
                            .zipWith(recipeDao.getInstructionsById(recipe.idRecipe),
                                    BiFunction<MutableList<IngredientEntity>, MutableList<InstructionEntity>, Recipe> { listIngredients, listInstruction ->
                                        recipeMapper.mapDetailRecipe(recipe, listIngredients, listInstruction)
                                    })
                }.toList().toFlowable()
            }
        }
    }

    override fun loadIngredientsTitle(): Single<List<String>> = recipeDao.loadIngredientsTitle()

    override fun loadRemoteRecipes(): Flowable<MutableList<Recipe>> = recipeService.getAllRecipes("ru")

    override fun sendReportMessage(reportMessage: ReportMessage): Single<Response> = recipeService.sendReportMessage(reportMessage)

    override fun getInstructionsById(idRecipe: Int): Single<MutableList<Instruction>> = recipeDao.getInstructionsById(idRecipe).map { recipeMapper.reverseInstructions(it) }
}
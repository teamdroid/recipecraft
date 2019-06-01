package ru.teamdroid.recipecraft.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.teamdroid.recipecraft.data.api.FeedbackMessage
import ru.teamdroid.recipecraft.data.api.Response
import ru.teamdroid.recipecraft.data.model.Instruction
import ru.teamdroid.recipecraft.data.model.Recipe

interface RecipesDataSource {
    fun loadLocalRecipes(sortType: String): Flowable<MutableList<Recipe>>
    fun loadBookmarkRecipes(): Flowable<MutableList<Recipe>>
    fun loadRemoteRecipes(): Flowable<MutableList<Recipe>>
    fun addRecipes(recipes: MutableList<Recipe>): Completable
    fun addIngredients(recipes: MutableList<Recipe>): Completable
    fun addRecipeIngredients(recipes: MutableList<Recipe>): Completable
    fun addInstructions(recipes: MutableList<Recipe>): Completable
    fun addUnitMeasure(recipes: MutableList<Recipe>): Completable
    fun bookmark(recipe: Recipe): Completable
    fun sendReportMessage(feedbackMessage: FeedbackMessage): Single<Response>
    fun getInstructionsById(idRecipe: Int): Single<MutableList<Instruction>>
    fun loadIngredientsTitle(): Single<List<String>>
    fun findRecipesByIngredients(listIngredients: List<String>, count: Int): Flowable<MutableList<Recipe>>
}
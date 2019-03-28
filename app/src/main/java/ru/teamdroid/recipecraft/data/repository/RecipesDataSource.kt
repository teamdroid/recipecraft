package ru.teamdroid.recipecraft.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.teamdroid.recipecraft.data.api.ReportMessage
import ru.teamdroid.recipecraft.data.api.Response
import ru.teamdroid.recipecraft.data.model.Recipe

interface RecipesDataSource {
    fun loadLocalRecipe(): Flowable<MutableList<Recipe>>
    fun loadBookmarkRecipes(): Flowable<MutableList<Recipe>>
    fun loadRemoteRecipe(): Flowable<MutableList<Recipe>>
    fun addRecipe(recipes: Recipe)
    fun addRecipes(recipes: MutableList<Recipe>): Completable
    fun addIngredients(recipes: MutableList<Recipe>): Completable
    fun addRecipeIngredients(recipes: MutableList<Recipe>): Completable
    fun bookmark(recipe: Recipe): Completable
    fun sendReportMessage(reportMessage: ReportMessage): Single<Response>
}
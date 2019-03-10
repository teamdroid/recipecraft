package ru.teamdroid.recipecraft.concept.data.api

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.teamdroid.recipecraft.concept.data.model.Recipes

interface RecipeService {
    @GET("getRecipes?")
    fun getAllRecipes(@Query("lang") language: String): Flowable<MutableList<Recipes>>
}
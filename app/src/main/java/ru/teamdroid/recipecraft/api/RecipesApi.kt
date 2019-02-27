package ru.teamdroid.recipecraft.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.teamdroid.recipecraft.room.entity.Recipe

interface RecipesApi {
    @GET("getRecipes?")
    fun getAllRecipes(@Query("lang") language: String): Observable<MutableList<Recipe>>
}
package ru.teamdroid.recipecraft

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.teamdroid.Ingredients

interface IngredientsApi {
    @GET("getAllIngredients?")
    fun getAllIngredients(@Query("lang") language: String): Observable<List<Ingredients>>
}
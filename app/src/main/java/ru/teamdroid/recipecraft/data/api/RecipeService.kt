package ru.teamdroid.recipecraft.data.api

import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.teamdroid.recipecraft.data.model.Recipe

interface RecipeService {
    @GET("getRecipes?")
    fun getAllRecipes(@Query("lang") language: String): Flowable<MutableList<Recipe>>

    @POST("sendFeedbackMessage/")
    fun sendFeedback(@Body feedbackMessage: FeedbackMessage): Single<Response>
}
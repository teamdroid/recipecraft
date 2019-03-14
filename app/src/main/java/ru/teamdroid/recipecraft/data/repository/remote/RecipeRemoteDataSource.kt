package ru.teamdroid.recipecraft.data.repository.remote

import io.reactivex.Flowable
import ru.teamdroid.recipecraft.data.api.RecipeService
import ru.teamdroid.recipecraft.data.model.Recipes
import ru.teamdroid.recipecraft.data.repository.RecipesDataSource
import javax.inject.Inject

class RecipeRemoteDataSource @Inject constructor(private val recipeService: RecipeService) : RecipesDataSource {

    override fun loadRecipe(forceRemote: Boolean): Flowable<List<Recipes>> {
        return recipeService.getAllRecipes("ru")
    }

//    fun loadQuestions(forceRemote: Boolean): Flowable<List<Question>> {
//        return questionService.loadQuestionsByTag(Config.ANDROID_QUESTION_TAG).map(???({ QuestionResponse.getQuestions() }))
//    }
//
    override fun addRecipe(recipes: Recipes) {
        throw UnsupportedOperationException("Unsupported operation")
    }

    override fun clearData() {
        //Currently, we do not need this for remote source.
        throw UnsupportedOperationException("Unsupported operation")
    }
}
package ru.teamdroid.recipecraft.concept.ui.navigation

import com.arellomobile.mvp.MvpView
import ru.teamdroid.recipecraft.concept.data.model.Recipes

interface RecipesContract {

    interface View : MvpView {
        fun showRecipes(recipes: MutableList<Recipes>)
    }

    interface Presenter : RecipesContract.View {
        fun loadQuestions(onlineRequired: Boolean)

        fun getQuestion(questionId: Long)

        fun search(questionTitle: String)
    }
}
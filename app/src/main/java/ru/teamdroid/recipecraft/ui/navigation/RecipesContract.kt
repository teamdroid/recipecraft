package ru.teamdroid.recipecraft.ui.navigation

import ru.teamdroid.recipecraft.data.model.Recipes

interface RecipesContract {

    interface View  {
        fun showRecipes(recipes: MutableList<Recipes>)
    }

    interface Presenter : View {
        fun loadQuestions(onlineRequired: Boolean)

        fun getQuestion(questionId: Long)

        fun search(questionTitle: String)
    }
}
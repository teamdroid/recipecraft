package ru.teamdroid.recipecraft.ui.navigation

import ru.teamdroid.recipecraft.data.model.Recipe

interface RecipesContract {

    interface View  {
        fun showRecipes(recipes: List<Recipe>)
    }

    interface Presenter : View {
        fun loadRecipes(onlineRequired: Boolean)
    }
}
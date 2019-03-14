package ru.teamdroid.recipecraft.ui.navigation

import ru.teamdroid.recipecraft.data.model.Recipes

interface RecipesContract {

    interface View  {
        fun showRecipes(recipes: List<Recipes>)
    }

    interface Presenter : View {
        fun loadRecipes(onlineRequired: Boolean)
    }
}
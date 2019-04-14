package ru.teamdroid.recipecraft.ui.navigation

import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.ui.base.BasePresenter

interface FavoritesContract {
    interface View {
        fun showRecipes(recipes: MutableList<Recipe>)
    }

    interface Presenter : BasePresenter<View> {
        fun loadRecipes()
    }
}
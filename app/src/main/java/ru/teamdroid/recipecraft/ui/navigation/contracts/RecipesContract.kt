package ru.teamdroid.recipecraft.ui.navigation.contracts

import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.ui.base.BasePresenter

interface RecipesContract {
    interface View {
        fun showRecipes(recipes: MutableList<Recipe>)
        fun showBookmarked(isBookmarked: Boolean)
    }

    interface Presenter : BasePresenter<View> {
        fun loadRecipes(onlineRequired: Boolean)
    }
}
package ru.teamdroid.recipecraft.ui.navigation.contracts

import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.ui.base.BasePresenter

interface CraftRecipeContract {
    interface View {
        fun setIngredientsTitle(listIngredientsTitle: List<String>)
        fun showRecipe(listRecipe: MutableList<Recipe>)
        fun showBookmarked(isBookmarked: Boolean)
    }

    interface Presenter : BasePresenter<View> {
        fun loadIngredientsTitle()
        fun findRecipeByIngredients(listIngredients: List<String>, count: Int)
    }
}
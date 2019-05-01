package ru.teamdroid.recipecraft.ui.navigation.contracts

import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.ui.base.BasePresenter

interface DetailRecipeContract {
    interface View {
        fun showBookmarked(isBookmarked: Boolean)
    }

    interface Presenter : BasePresenter<View> {
        fun bookmarkRecipe(recipe : Recipe)
    }
}
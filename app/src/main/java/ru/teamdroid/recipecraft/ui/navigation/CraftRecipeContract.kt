package ru.teamdroid.recipecraft.ui.navigation

import ru.teamdroid.recipecraft.ui.base.BasePresenter

interface CraftRecipeContract {
    interface View {
        fun getIngredientsTitle(listIngredientsTitle: MutableList<String>)
    }

    interface Presenter : BasePresenter<View> {

    }
}
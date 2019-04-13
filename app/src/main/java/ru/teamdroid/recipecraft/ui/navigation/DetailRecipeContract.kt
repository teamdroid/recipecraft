package ru.teamdroid.recipecraft.ui.navigation

import ru.teamdroid.recipecraft.ui.base.BasePresenter

interface DetailRecipeContract {
    interface View

    interface Presenter : BasePresenter<View>
}
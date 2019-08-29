package ru.teamdroid.recipecraft.ui.navigation.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*
import ru.teamdroid.recipecraft.data.model.Recipe

interface RecipeView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showRecipes(listRecipes: MutableList<Recipe>)

    @StateStrategyType(SkipStrategy::class)
    fun showBookmarked(isBookmarked: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun showLoadProgressBar()

    @StateStrategyType(SkipStrategy::class)
    fun hideLoadProgressBar()

}
package ru.teamdroid.recipecraft.ui.navigation.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.teamdroid.recipecraft.data.model.Recipe


interface RecipeView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showRecipes(listRecipes: MutableList<Recipe>)

    @StateStrategyType(SkipStrategy::class)
    fun showBookmarked(isBookmarked: Boolean)
}
package ru.teamdroid.recipecraft.ui.navigation.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.teamdroid.recipecraft.data.model.Recipe

@StateStrategyType(AddToEndSingleStrategy::class)
interface RecipeView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showRecipes(recipes: MutableList<Recipe>)
}
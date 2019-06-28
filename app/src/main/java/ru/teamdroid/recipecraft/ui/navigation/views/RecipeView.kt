package ru.teamdroid.recipecraft.ui.navigation.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.teamdroid.recipecraft.data.model.Recipe

@StateStrategyType(AddToEndSingleStrategy::class)
interface RecipeView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showRecipes(recipes: MutableList<Recipe>)
}
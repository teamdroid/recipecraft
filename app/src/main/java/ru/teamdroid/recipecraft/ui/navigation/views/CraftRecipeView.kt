package ru.teamdroid.recipecraft.ui.navigation.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.teamdroid.recipecraft.data.model.Recipe

@StateStrategyType(AddToEndSingleStrategy::class)
interface CraftRecipeView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun setIngredientsTitle(listIngredientsTitle: List<String>)

    @StateStrategyType(SingleStateStrategy::class)
    fun showRecipes(listRecipe: MutableList<Recipe>)

    @StateStrategyType(SkipStrategy::class)
    fun showBookmarked(isBookmarked: Boolean)
}

package ru.teamdroid.recipecraft.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.teamdroid.recipecraft.room.entity.Recipes

@StateStrategyType(AddToEndSingleStrategy::class)
interface RecipesView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun onSuccessLoad(list: MutableList<Recipes>)

    @StateStrategyType(SkipStrategy::class)
    fun onErrorLoad(error: Throwable)

    @StateStrategyType(SkipStrategy::class)
    fun setInvisibleRefreshing()
}
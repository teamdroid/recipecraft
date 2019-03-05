package ru.teamdroid.recipecraft.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.teamdroid.recipecraft.room.entity.Recipe

interface RecipesView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun onSuccessLoad(list: MutableList<Recipe>)
    @StateStrategyType(SkipStrategy::class)
    fun onErrorLoad(error: Throwable)
    fun setInvisibleRefreshing()
}
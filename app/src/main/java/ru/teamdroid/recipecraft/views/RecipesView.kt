package ru.teamdroid.recipecraft.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.teamdroid.recipecraft.room.entity.Recipe

@StateStrategyType(AddToEndSingleStrategy::class)
interface RecipesView : MvpView {
    fun onSuccessLoad(list: MutableList<Recipe>)
    fun onErrorLoad(error: Throwable)
    fun setInvisibleRefreshing()
}
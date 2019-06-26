package ru.teamdroid.recipecraft.ui.navigation.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface FeedbackView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun onSuccess()

    @StateStrategyType(SkipStrategy::class)
    fun onFailure()
}
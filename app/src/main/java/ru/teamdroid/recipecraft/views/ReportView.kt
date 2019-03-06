package ru.teamdroid.recipecraft.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface ReportView: MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun onSuccess()

    @StateStrategyType(SkipStrategy::class)
    fun onFailure()
}
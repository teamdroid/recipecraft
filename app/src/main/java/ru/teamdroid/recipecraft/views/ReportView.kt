package ru.teamdroid.recipecraft.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface ReportView: MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onSuccess()
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onFailure()
}
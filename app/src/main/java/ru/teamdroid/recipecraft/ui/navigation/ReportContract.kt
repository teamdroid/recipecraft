package ru.teamdroid.recipecraft.ui.navigation

import ru.teamdroid.recipecraft.ui.base.BasePresenter

interface ReportContract {
    interface View {
        fun onSuccess()
        fun onFailure()
    }

    interface Presenter : BasePresenter<View>
}
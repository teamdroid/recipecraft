package ru.teamdroid.recipecraft.ui.navigation.modules

import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.ui.navigation.contracts.ReportContract

@Module
class ReportPresenterModule(private val view: ReportContract.View) {
    @Provides
    fun provideView(): ReportContract.View {
        return view
    }
}
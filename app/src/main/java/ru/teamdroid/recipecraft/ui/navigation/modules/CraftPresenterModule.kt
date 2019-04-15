package ru.teamdroid.recipecraft.ui.navigation.modules

import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.ui.navigation.contracts.CraftRecipeContract

@Module
class CraftPresenterModule(private val view: CraftRecipeContract.View) {
    @Provides
    fun provideView(): CraftRecipeContract.View {
        return view
    }
}
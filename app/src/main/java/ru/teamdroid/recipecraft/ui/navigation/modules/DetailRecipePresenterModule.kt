package ru.teamdroid.recipecraft.ui.navigation.modules

import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.ui.navigation.contracts.DetailRecipeContract

@Module
class DetailRecipePresenterModule(private val view: DetailRecipeContract.View) {

    @Provides
    fun provideView(): DetailRecipeContract.View {
        return view
    }
}
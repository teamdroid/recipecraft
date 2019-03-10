package ru.teamdroid.recipecraft.concept.ui.navigation.modules

import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.concept.ui.navigation.RecipesContract

@Module
class RecipesPresenterModule(private val view: RecipesContract.View) {

    @Provides
    fun provideView(): RecipesContract.View {
        return view
    }
}

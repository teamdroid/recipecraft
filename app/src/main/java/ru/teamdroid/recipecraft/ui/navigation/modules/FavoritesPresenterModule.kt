package ru.teamdroid.recipecraft.ui.navigation.modules

import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.ui.navigation.FavoritesContract

@Module
class FavoritesPresenterModule(private val view: FavoritesContract.View) {

    @Provides
    fun provideView(): FavoritesContract.View {
        return view
    }
}
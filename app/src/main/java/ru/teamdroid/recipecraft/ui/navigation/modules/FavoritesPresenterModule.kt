package ru.teamdroid.recipecraft.ui.navigation.modules

import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.ui.navigation.views.FavoritesView

@Module
class FavoritesPresenterModule(private val view: FavoritesView) {
    @Provides
    fun provideView(): FavoritesView {
        return view
    }
}
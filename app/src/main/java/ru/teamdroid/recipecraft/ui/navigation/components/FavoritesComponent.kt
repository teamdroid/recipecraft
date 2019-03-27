package ru.teamdroid.recipecraft.ui.navigation.components

import dagger.Component
import ru.teamdroid.recipecraft.data.RecipeRepositoryComponent
import ru.teamdroid.recipecraft.ui.base.ActivityScope
import ru.teamdroid.recipecraft.ui.navigation.fragments.FavoritesFragment
import ru.teamdroid.recipecraft.ui.navigation.modules.FavoritesPresenterModule
import ru.teamdroid.recipecraft.util.schedulers.SchedulerModule

@ActivityScope
@Component(modules = [FavoritesPresenterModule::class, SchedulerModule::class], dependencies = [RecipeRepositoryComponent::class])
interface FavoritesComponent {
    fun inject(fragment: FavoritesFragment)
}



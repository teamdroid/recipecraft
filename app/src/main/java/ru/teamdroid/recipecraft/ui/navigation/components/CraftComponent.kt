package ru.teamdroid.recipecraft.ui.navigation.components

import dagger.Component
import ru.teamdroid.recipecraft.data.components.RecipeRepositoryComponent
import ru.teamdroid.recipecraft.ui.base.ActivityScope
import ru.teamdroid.recipecraft.ui.navigation.fragments.CraftFragment
import ru.teamdroid.recipecraft.util.schedulers.SchedulerModule

@ActivityScope
@Component(modules = [SchedulerModule::class], dependencies = [RecipeRepositoryComponent::class])
interface CraftComponent {
    fun inject(fragment: CraftFragment)
}
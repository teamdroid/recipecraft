package ru.teamdroid.recipecraft.ui.navigation.components

import dagger.Component
import ru.teamdroid.recipecraft.data.components.RecipeRepositoryComponent
import ru.teamdroid.recipecraft.ui.base.ActivityScope
import ru.teamdroid.recipecraft.ui.navigation.fragments.FeedbackFragment
import ru.teamdroid.recipecraft.ui.navigation.modules.ReportPresenterModule
import ru.teamdroid.recipecraft.util.schedulers.SchedulerModule

@ActivityScope
@Component(modules = [ReportPresenterModule::class, SchedulerModule::class], dependencies = [RecipeRepositoryComponent::class])
interface ReportComponent {
    fun inject(fragment: FeedbackFragment)
}
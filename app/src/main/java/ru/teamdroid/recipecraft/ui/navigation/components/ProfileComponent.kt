package ru.teamdroid.recipecraft.ui.navigation.components

import dagger.Component
import ru.teamdroid.recipecraft.AppModule
import ru.teamdroid.recipecraft.ui.base.ActivityScope
import ru.teamdroid.recipecraft.ui.navigation.fragments.ProfileFragment
import ru.teamdroid.recipecraft.ui.navigation.modules.ProfilePresenterModule
import ru.teamdroid.recipecraft.util.schedulers.SchedulerModule

@ActivityScope
@Component(modules = [AppModule::class, ProfilePresenterModule::class, SchedulerModule::class])
interface ProfileComponent {
    fun inject(fragment: ProfileFragment)
}

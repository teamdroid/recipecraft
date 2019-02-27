package ru.teamdroid.recipecraft.di.components

import dagger.Subcomponent
import dagger.android.AndroidInjector
import ru.teamdroid.recipecraft.MainActivity

@Subcomponent
interface MainComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}
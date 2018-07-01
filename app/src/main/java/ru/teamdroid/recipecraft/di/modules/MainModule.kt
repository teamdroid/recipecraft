package ru.teamdroid.recipecraft.di.modules

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import ru.teamdroid.recipecraft.MainActivity
import ru.teamdroid.recipecraft.di.components.MainComponent

@Module(subcomponents = [(MainComponent::class)])
abstract class MainModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    internal abstract fun bindsToDoActivityInjectorFactory(builder: MainComponent.Builder): AndroidInjector.Factory<out Activity>
}
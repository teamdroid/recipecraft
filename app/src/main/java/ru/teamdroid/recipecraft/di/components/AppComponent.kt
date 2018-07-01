package ru.teamdroid.recipecraft.di.components

import dagger.Component
import ru.teamdroid.recipecraft.MainApp
import ru.teamdroid.recipecraft.di.modules.AppModule
import ru.teamdroid.recipecraft.di.modules.MainModule

@Component(modules = [(AppModule::class), (MainModule::class)])
interface AppComponent {
    fun inject(application: MainApp)
}
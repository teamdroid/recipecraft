package ru.teamdroid.recipecraft

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Application) {
    @Provides
    fun provideContext(): Context {
        return context
    }
}

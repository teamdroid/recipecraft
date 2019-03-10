package ru.teamdroid.recipecraft.concept

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }
}

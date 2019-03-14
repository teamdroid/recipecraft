package ru.teamdroid.recipecraft.data

import dagger.Component
import ru.teamdroid.recipecraft.AppModule
import ru.teamdroid.recipecraft.data.repository.RecipeRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [RecipeRepositoryModule::class, AppModule::class, ApiServiceModule::class, DatabaseModule::class])
interface RecipeRepositoryComponent {
    fun provideRecipeRepository(): RecipeRepository
}
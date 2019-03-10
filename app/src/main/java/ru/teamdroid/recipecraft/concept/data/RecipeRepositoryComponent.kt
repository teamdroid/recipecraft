package ru.teamdroid.recipecraft.concept.data

import dagger.Component
import ru.teamdroid.recipecraft.concept.AppModule
import ru.teamdroid.recipecraft.concept.repository.RecipeRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [RecipeRepositoryModule::class, AppModule::class, ApiServiceModule::class, DatabaseModule::class])
interface RecipeRepositoryComponent {
    fun provideRecipeRepository(): RecipeRepository
}
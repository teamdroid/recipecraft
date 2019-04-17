package ru.teamdroid.recipecraft.data.components

import dagger.Component
import ru.teamdroid.recipecraft.AppModule
import ru.teamdroid.recipecraft.data.modules.ApiServiceModule
import ru.teamdroid.recipecraft.data.modules.DatabaseModule
import ru.teamdroid.recipecraft.data.modules.MapperModule
import ru.teamdroid.recipecraft.data.modules.RecipeRepositoryModule
import ru.teamdroid.recipecraft.data.repository.RecipeRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [RecipeRepositoryModule::class, AppModule::class, ApiServiceModule::class, DatabaseModule::class, MapperModule::class])
interface RecipeRepositoryComponent {
    fun provideRecipeRepository(): RecipeRepository
}
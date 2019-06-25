package ru.teamdroid.recipecraft.data.components

import dagger.Component
import ru.teamdroid.recipecraft.AppModule
import ru.teamdroid.recipecraft.data.modules.*
import ru.teamdroid.recipecraft.data.repository.RecipeRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [RecipeRepositoryModule::class, AppModule::class, ApiServiceModule::class, DatabaseModule::class, MapperModule::class, FileUtilsModule::class])
interface RecipeRepositoryComponent {
    fun provideRecipeRepository(): RecipeRepository
}
package ru.teamdroid.recipecraft.data

import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.data.repository.RecipeLocalDataSource
import ru.teamdroid.recipecraft.data.repository.RecipesDataSource
import javax.inject.Singleton

@Module
class RecipeRepositoryModule {
    @Provides
    @Singleton
    fun provideRemoteDataSource(recipeDataSource: RecipeLocalDataSource): RecipesDataSource {
        return recipeDataSource
    }
}

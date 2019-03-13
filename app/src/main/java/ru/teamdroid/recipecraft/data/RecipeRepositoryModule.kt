package ru.teamdroid.recipecraft.data

import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.repository.Local
import ru.teamdroid.recipecraft.repository.RecipesDataSource
import ru.teamdroid.recipecraft.repository.Remote
import ru.teamdroid.recipecraft.repository.local.RecipeLocalDataSource
import ru.teamdroid.recipecraft.repository.remote.RecipeRemoteDataSource
import javax.inject.Singleton

@Module
class RecipeRepositoryModule {

    @Provides
    @Local
    @Singleton
    fun provideLocalDataSource(recipeLocalDataSource: RecipeLocalDataSource): RecipesDataSource {
        return recipeLocalDataSource
    }

    @Provides
    @Remote
    @Singleton
    fun provideRemoteDataSource(recipeRemoteDataSource: RecipeRemoteDataSource): RecipesDataSource {
        return recipeRemoteDataSource
    }

}

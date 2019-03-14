package ru.teamdroid.recipecraft.data

import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.data.repository.Local
import ru.teamdroid.recipecraft.data.repository.RecipesDataSource
import ru.teamdroid.recipecraft.data.repository.Remote
import ru.teamdroid.recipecraft.data.repository.local.RecipeLocalDataSource
import ru.teamdroid.recipecraft.data.repository.remote.RecipeRemoteDataSource
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

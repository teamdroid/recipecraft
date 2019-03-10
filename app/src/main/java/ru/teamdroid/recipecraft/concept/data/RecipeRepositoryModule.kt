package ru.teamdroid.recipecraft.concept.data

import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.concept.repository.Local
import ru.teamdroid.recipecraft.concept.repository.RecipesDataSource
import ru.teamdroid.recipecraft.concept.repository.Remote
import ru.teamdroid.recipecraft.concept.repository.local.RecipeLocalDataSource
import ru.teamdroid.recipecraft.concept.repository.remote.RecipeRemoteDataSource
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

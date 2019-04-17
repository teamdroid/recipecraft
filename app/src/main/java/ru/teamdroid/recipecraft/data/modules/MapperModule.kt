package ru.teamdroid.recipecraft.data.modules

import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.data.base.RecipeMapper
import javax.inject.Singleton

@Module
class MapperModule {
    @Provides
    @Singleton
    internal fun provideRecipeMapper(): RecipeMapper = RecipeMapper()
}
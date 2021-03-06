package ru.teamdroid.recipecraft.data.modules

import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.data.base.RecipeMapper
import ru.teamdroid.recipecraft.util.FileUtils
import javax.inject.Singleton

@Module
class MapperModule {
    @Provides
    @Singleton
    internal fun provideRecipeMapper(fileUtils: FileUtils): RecipeMapper = RecipeMapper(fileUtils)
}
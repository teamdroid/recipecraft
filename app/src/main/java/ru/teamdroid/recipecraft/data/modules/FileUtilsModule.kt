package ru.teamdroid.recipecraft.data.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.util.FileUtils
import javax.inject.Singleton

@Module
class FileUtilsModule {
    @Provides
    @Singleton
    internal fun provideFileUtils(context: Context): FileUtils = FileUtils(context)
}
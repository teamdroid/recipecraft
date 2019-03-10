package ru.teamdroid.recipecraft.concept.data

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.concept.data.database.RecipecraftDB
import ru.teamdroid.recipecraft.concept.data.database.RecipesDao
import javax.inject.Named
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Named(DATABASE)
    internal fun provideDatabaseName(): String {
        return Config.DATABASE_NAME
    }

    @Provides
    @Singleton
    internal fun provideRecipecraftDao(context: Context, @Named(DATABASE) databaseName: String): RecipecraftDB{
        return Room.databaseBuilder(context, RecipecraftDB::class.java, databaseName).build()
    }

    @Provides
    @Singleton
    internal fun provideRecipeDao(recipecraftDB:RecipecraftDB): RecipesDao {
        return recipecraftDB.recipeDao()
    }

    companion object {
        private const val DATABASE = "recipecraft"
    }
}

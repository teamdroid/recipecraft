package ru.teamdroid.recipecraft.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.room.RecipecraftRoomDatabase

@Module
class AppModule(private val context: Context) {

    @Provides
    fun providesAppContext() = context

    @Provides
    fun providesAppDatabase(context: Context): RecipecraftRoomDatabase =
            Room.databaseBuilder(context, RecipecraftRoomDatabase::class.java, "RecipecraftRoomDatabase").build()

    @Provides
    fun providesItemDao(database: RecipecraftRoomDatabase) = database.itemDao()
}
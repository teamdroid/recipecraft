package ru.teamdroid.recipecraft.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.room.ItemRoomDatabase

@Module
class AppModule(private val context: Context) {

    @Provides
    fun providesAppContext() = context

    @Provides
    fun providesAppDatabase(context: Context): ItemRoomDatabase =
            Room.databaseBuilder(context, ItemRoomDatabase::class.java, "ItemRoomDatabase").build()

    @Provides
    fun providesItemDao(database: ItemRoomDatabase) = database.itemDao()
}
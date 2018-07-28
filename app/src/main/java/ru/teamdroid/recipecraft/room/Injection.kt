package ru.teamdroid.recipecraft.room

import android.content.Context
import ru.teamdroid.recipecraft.room.dao.IngredientsDao

/**
 * Enables injection of data sources.
 */
object Injection {

    private fun provideUserDataSource(context: Context): IngredientsDao {
        val database = RecipecraftRoomDatabase.getInstance(context)
        return database.itemDao()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dataSource = provideUserDataSource(context)
        return ViewModelFactory(dataSource)
    }
}

package ru.teamdroid.recipecraft.room

import android.content.Context
import ru.teamdroid.recipecraft.room.dao.ItemDao

/**
 * Enables injection of data sources.
 */
object Injection {

    private fun provideUserDataSource(context: Context): ItemDao {
        val database = ItemRoomDatabase.getInstance(context)
        return database.itemDao()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dataSource = provideUserDataSource(context)
        return ViewModelFactory(dataSource)
    }
}

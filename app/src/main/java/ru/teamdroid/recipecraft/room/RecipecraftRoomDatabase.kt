package ru.teamdroid.recipecraft.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import ru.teamdroid.recipecraft.room.dao.IngredientsDao
import ru.teamdroid.recipecraft.room.entity.Ingredients

@Database(entities = [(Ingredients::class)], version = 1, exportSchema = false)
abstract class RecipecraftRoomDatabase : RoomDatabase() {
    abstract fun itemDao(): IngredientsDao

    companion object {

        @Volatile
        private var INSTANCE: RecipecraftRoomDatabase? = null

        fun getInstance(context: Context): RecipecraftRoomDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        RecipecraftRoomDatabase::class.java, "Recipecraft.db")
                        .build()
    }
}
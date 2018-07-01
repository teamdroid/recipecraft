package ru.teamdroid.recipecraft.room.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import ru.teamdroid.recipecraft.room.entity.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM ingredients")
    fun getAll(): Flowable<MutableList<Item>>

    @Query("SELECT * FROM ingredients WHERE id = :id")
    fun getById(id: Int): Single<Item>

    @Query("DELETE FROM ingredients")
    fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Item)

    @Update
    fun update(item: Item)

    @Delete
    fun delete(iem: Item)
}
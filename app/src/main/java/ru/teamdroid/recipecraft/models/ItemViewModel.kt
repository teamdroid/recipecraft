package ru.teamdroid.recipecraft.models

import android.arch.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.teamdroid.recipecraft.room.dao.ItemDao
import ru.teamdroid.recipecraft.room.entity.Item

class ItemViewModel(private val dataSource: ItemDao) : ViewModel() {

    fun getTitle(id: Int): Single<String> {
        return dataSource.getById(id)
                .map { item -> item.title }
    }

    fun getAll() : Flowable<MutableList<Item>> {
        return dataSource.getAll()
    }

    fun insert(item: Item): Completable {
        return Completable.fromAction {
            dataSource.insert(item)
        }
    }

    fun clearAll(): Completable {
        return Completable.fromAction {
            dataSource.clearAll()
        }
    }

    fun updateTitle(id: Int, title: String): Completable {
        return Completable.fromAction {
            val item = Item(id, title)
            dataSource.insert(item)
        }
    }
}

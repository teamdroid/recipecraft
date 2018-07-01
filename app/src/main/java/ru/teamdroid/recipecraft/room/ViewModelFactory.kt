package ru.teamdroid.recipecraft.room

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ru.teamdroid.recipecraft.models.ItemViewModel
import ru.teamdroid.recipecraft.room.dao.ItemDao

class ViewModelFactory(private val dataSource: ItemDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            return ItemViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
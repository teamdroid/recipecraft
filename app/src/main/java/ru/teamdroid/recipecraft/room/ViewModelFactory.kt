package ru.teamdroid.recipecraft.room

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ru.teamdroid.recipecraft.models.IngredientsViewModel
import ru.teamdroid.recipecraft.room.dao.IngredientsDao

class ViewModelFactory(private val dataSource: IngredientsDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientsViewModel::class.java)) {
            return IngredientsViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
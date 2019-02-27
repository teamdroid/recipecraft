package ru.teamdroid.recipecraft.room

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ru.teamdroid.recipecraft.room.models.IngredientsViewModel
import ru.teamdroid.recipecraft.room.models.RecipesViewModel
import ru.teamdroid.recipecraft.room.dao.IngredientsDao

import ru.teamdroid.recipecraft.room.dao.RecipesDao

class ViewModelFactory(private val dataSource: Any) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipesViewModel::class.java)) {
            return RecipesViewModel(dataSource as RecipesDao) as T
        } else if (modelClass.isAssignableFrom(IngredientsViewModel::class.java)) {
            return IngredientsViewModel(dataSource as IngredientsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
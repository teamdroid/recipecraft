package ru.teamdroid.recipecraft.room

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ru.teamdroid.recipecraft.room.models.IngredientsViewModel
import ru.teamdroid.recipecraft.room.models.RecipesViewModel
import ru.teamdroid.recipecraft.room.dao.IngredientsDao

import ru.teamdroid.recipecraft.room.dao.RecipesDao

class ViewModelFactory(private val dataSource: Any) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RecipesViewModel::class.java) -> RecipesViewModel(dataSource as RecipesDao) as T
            modelClass.isAssignableFrom(IngredientsViewModel::class.java) -> IngredientsViewModel(dataSource as IngredientsDao) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
package ru.teamdroid.recipecraft.room.models

import android.arch.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.teamdroid.recipecraft.room.dao.IngredientsDao
import ru.teamdroid.recipecraft.room.entity.Ingredients

class IngredientsViewModel(private val dataSource: IngredientsDao) : ViewModel() {

    fun getTitleIngredients(id: Int): Single<String> {
        return dataSource.getByIdIngredients(id)
                .map { ingredient -> ingredient.title }
    }

    fun getAllIngredients(): Flowable<MutableList<Ingredients>> {
        return dataSource.getAllIngredients()
    }

    fun insertIngredients(ingredients: Ingredients): Completable {
        return Completable.fromAction {
            dataSource.insertIngredients(ingredients)
        }
    }

    fun clearAllIngredients(): Completable {
        return Completable.fromAction {
            dataSource.clearAllIngredients()
        }
    }

    fun updateTitleIngredients(id: Int, title: String): Completable {
        return Completable.fromAction {
            val ingredient = Ingredients(id, title)
            dataSource.insertIngredients(ingredient)
        }
    }
}

package ru.teamdroid.recipecraft.views

import com.arellomobile.mvp.MvpView
import ru.teamdroid.recipecraft.room.entity.Recipe

interface RecipesView : MvpView {
    fun onSuccessLoad(list: MutableList<Recipe>)
    fun onErrorLoad(error: Throwable)
    fun setInvisibleRefreshing()
}
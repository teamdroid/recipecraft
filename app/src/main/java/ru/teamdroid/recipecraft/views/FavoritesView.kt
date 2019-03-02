package ru.teamdroid.recipecraft.views

import com.arellomobile.mvp.MvpView
import ru.teamdroid.recipecraft.room.entity.Recipe

interface FavoritesView : MvpView {
    fun onSuccessLoad(list: MutableList<Recipe>)
}
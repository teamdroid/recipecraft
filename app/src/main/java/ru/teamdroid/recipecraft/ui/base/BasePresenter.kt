package ru.teamdroid.recipecraft.ui.base

interface BasePresenter<V> {
    fun onAttachView()
    fun onDetachView()
    fun onDestroy()
}
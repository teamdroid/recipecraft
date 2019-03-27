package ru.teamdroid.recipecraft.ui.base

interface BasePresenter<V> {
    fun onAttach()
    fun onDestroy()
}
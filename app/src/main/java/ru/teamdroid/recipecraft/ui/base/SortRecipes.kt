package ru.teamdroid.recipecraft.ui.base

object SortRecipes {
    const val ByNewer = "ByNewer"
    const val ByIngredients = "ByIngredients"
    const val ByTime = "ByTime"
    const val ByPortion = "ByPortion"

    val sort = listOf(ByNewer, ByIngredients, ByTime, ByPortion)
}
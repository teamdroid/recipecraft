package ru.teamdroid.recipecraft.ui.base

object SortRecipes {
    const val ByNewer = "idRecipe"
    const val ByIngredients = "countIngredients"
    const val ByTime = "time"
    const val ByPortion = "portion"

    val sort = listOf(ByNewer, ByIngredients, ByTime, ByPortion)
}
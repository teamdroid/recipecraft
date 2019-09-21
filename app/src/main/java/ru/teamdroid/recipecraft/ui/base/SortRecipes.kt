package ru.teamdroid.recipecraft.ui.base

object SortRecipes {
    const val BLANK = "BLANK"
    const val BY_NEWER = "idRecipe"
    const val BY_INGREDIENTS = "countIngredients"
    const val BY_TIME = "time"
    const val BY_PORTION = "portion"

    val sort = listOf(BY_NEWER, BY_PORTION, BY_INGREDIENTS, BY_TIME)
}
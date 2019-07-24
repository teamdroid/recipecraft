package ru.teamdroid.recipecraft.ui.base

import androidx.recyclerview.widget.DiffUtil
import ru.teamdroid.recipecraft.data.model.Recipe

class RecipeDiffCallback(private val oldList: MutableList<Recipe>, private val newList: MutableList<Recipe>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].idRecipe == newList[newItemPosition].idRecipe
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldList = oldList[oldItemPosition]
        val newList = newList[newItemPosition]
        return oldList.isBookmarked == newList.isBookmarked
    }

}

package ru.teamdroid.recipecraft.ui.base.customs

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager

class CustomLinearLayoutManager constructor(context: Context) : LinearLayoutManager(context) {

    fun isOnNextPagePosition(): Boolean {
        val visibleItemCount = childCount
        val totalItemCount = itemCount
        val pastVisibleItems = findFirstVisibleItemPosition()

        return (visibleItemCount + pastVisibleItems) >= totalItemCount
    }

}


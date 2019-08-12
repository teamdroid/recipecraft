package ru.teamdroid.recipecraft.data

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager

class CustomLinearLayoutManager constructor(context: Context) : LinearLayoutManager(context) {

    fun isOnNextPagePosition(): Boolean {
        val visibleItemCount = childCount
        val totalItemCount = itemCount
        val pastVisibleItems = findFirstVisibleItemPosition()


        return (visibleItemCount + pastVisibleItems) >= totalItemCount / 2//6+10,30/2 = 15
    }

}


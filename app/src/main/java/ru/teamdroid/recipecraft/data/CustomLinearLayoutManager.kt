package ru.teamdroid.recipecraft.data

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager

class CustomLinearLayoutManager @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : LinearLayoutManager(context, attrs, defStyle, defStyleRes) {

    fun isOnNextPagePosition(): Boolean {
        val visibleItemCount = childCount
        val totalItemCount = itemCount
        val pastVisibleItems = findFirstVisibleItemPosition()


        return (visibleItemCount + pastVisibleItems) >= totalItemCount / 2//6+10,30/2 = 15
    }

}


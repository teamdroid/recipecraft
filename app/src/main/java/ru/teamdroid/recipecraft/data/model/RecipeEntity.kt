package ru.teamdroid.recipecraft.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "recipe")
data class RecipeEntity(
        @PrimaryKey @NonNull @ColumnInfo(name = "idRecipe")
        var idRecipe: Int = 0,
        @ColumnInfo(name = "title")
        var title: String = "",
        @ColumnInfo(name = "time")
        var time: Long = 0,
        @ColumnInfo(name = "portion")
        var portion: Int = 0,
        @ColumnInfo(name = "isBookmarked")
        var isBookmarked: Boolean = false
)



package ru.teamdroid.recipecraft.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class RecipeEntity(
        @PrimaryKey @ColumnInfo(name = "idRecipe")
        var idRecipe: Int = 0,
        @ColumnInfo(name = "title")
        var title: String = "",
        @ColumnInfo(name = "time")
        var time: Long = 0,
        @ColumnInfo(name = "portion")
        var portion: Int = 0,
        @ColumnInfo(name = "type")
        var type: String = "",
        @ColumnInfo(name = "isBookmarked")
        var isBookmarked: Boolean = false,
        @ColumnInfo(name = "countIngredients")
        var countIngredients: Int = 0
)



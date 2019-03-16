package ru.teamdroid.recipecraft.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "ingredient")
data class IngredientEntity(
        @PrimaryKey @NonNull @ColumnInfo(name = "idIngredient")
        var idIngredient: Int = 0,
        @ColumnInfo(name = "title")
        var title: String = ""
)
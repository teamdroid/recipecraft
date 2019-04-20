package ru.teamdroid.recipecraft.data.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient")
data class IngredientEntity(
        @PrimaryKey @NonNull @ColumnInfo(name = "idIngredient")
        var idIngredient: Int = 0,
        @ColumnInfo(name = "title")
        var title: String = "",
        @ColumnInfo(name = "amount")
        var amount: Double,
        @ColumnInfo(name = "idUnitMeasure", index = true)
        var idUnitMeasure: Int = 0,
        @ColumnInfo(name = "measureTitle")
        var title_unit_measure: String = ""
)
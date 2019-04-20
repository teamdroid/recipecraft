package ru.teamdroid.recipecraft.data.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unit_measure")
data class UnitMeasureEntity(
        @PrimaryKey @NonNull @ColumnInfo(name = "idUnitMeasure")
        val idUnitMeasure: Int = 0,
        @ColumnInfo(name = "title")
        val title: String
)
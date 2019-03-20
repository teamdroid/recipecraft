package ru.teamdroid.recipecraft.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "instruction")
data class Instruction(
        @PrimaryKey @NonNull @ColumnInfo(name = "id")
        val id: Int = 0,
        @ColumnInfo(name = "title")
        val title: String,
        @ColumnInfo(name = "amount")
        val amount: Int
)
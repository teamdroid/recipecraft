package ru.teamdroid.recipecraft.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "instruction")
data class Instruction(
        @PrimaryKey @NonNull @ColumnInfo(name = "id")
        val id: Int,
        @ColumnInfo(name = "title")
        val title: String,
        @ColumnInfo(name = "amount")
        val amount: Int
)
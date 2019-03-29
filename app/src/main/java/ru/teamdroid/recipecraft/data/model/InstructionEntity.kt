package ru.teamdroid.recipecraft.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "instruction", foreignKeys = [
    ForeignKey(entity = RecipeEntity::class,
            parentColumns = arrayOf("idRecipe"),
            childColumns = arrayOf("idRecipe"))])
data class InstructionEntity(
        @PrimaryKey @NonNull @ColumnInfo(name = "id")
        val idInstruction: Int = 0,
        @ColumnInfo(name = "idRecipe", index = true)
        var idRecipe: Int = 0,
        @ColumnInfo(name = "title")
        val title: String
)
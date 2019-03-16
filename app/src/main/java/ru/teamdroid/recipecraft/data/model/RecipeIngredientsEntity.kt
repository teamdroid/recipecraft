package ru.teamdroid.recipecraft.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "recipe_ingredients", foreignKeys = [
    ForeignKey(entity = RecipeEntity::class,
            parentColumns = arrayOf("idRecipe"),
            childColumns = arrayOf("idRecipe"),
            onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = IngredientEntity::class,
            parentColumns = arrayOf("idIngredient"),
            childColumns = arrayOf("idIngredient"),
            onDelete = ForeignKey.CASCADE)])
data class RecipeIngredientsEntity(
        @PrimaryKey @ColumnInfo(name = "id")
        var id: Int = 0,
        @ColumnInfo(name = "idRecipe")
        var idRecipe: Int = 0,
        @ColumnInfo(name = "idIngredient")
        var idIngredient: Int = 0
)

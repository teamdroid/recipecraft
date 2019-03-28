package ru.teamdroid.recipecraft.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
        @ColumnInfo(name = "idRecipe", index = true)
        var idRecipe: Int = 0,
        @ColumnInfo(name = "idIngredient", index = true)
        var idIngredient: Int = 0
)

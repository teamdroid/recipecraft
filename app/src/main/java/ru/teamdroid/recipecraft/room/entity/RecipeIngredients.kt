package ru.teamdroid.recipecraft.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = "recipe_ingredients", foreignKeys = [
    ForeignKey(entity = Recipe::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idRecipe"),
            onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Ingredients::class,
            parentColumns = arrayOf("idIngredient"),
            childColumns = arrayOf("idIngredient"),
            onDelete = ForeignKey.CASCADE)])
data class RecipeIngredients(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        @ColumnInfo(name = "idRecipe")
        val idRecipe: Int,
        @ColumnInfo(name = "idIngredient")
        val idIngredient: Int
) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt(), parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(idRecipe)
        parcel.writeInt(idIngredient)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Recipe> = object : Parcelable.Creator<Recipe> {
            override fun createFromParcel(parcel: Parcel): Recipe {
                return Recipe(parcel)
            }

            override fun newArray(size: Int): Array<Recipe?> {
                return arrayOfNulls(size)
            }
        }
    }
}

package ru.teamdroid.recipecraft.data.model

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = "recipe_ingredients", foreignKeys = [
    ForeignKey(entity = Recipes::class,
            parentColumns = arrayOf("idRecipe"),
            childColumns = arrayOf("idRecipe"),
            onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Ingredients::class,
            parentColumns = arrayOf("idIngredient"),
            childColumns = arrayOf("idIngredient"),
            onDelete = ForeignKey.CASCADE)])
data class RecipeIngredients(
        @PrimaryKey @ColumnInfo(name = "id")
        var id: Int = 0,
        @ColumnInfo(name = "idRecipe")
        var idRecipe: Int = 0,
        @ColumnInfo(name = "idIngredient")
        var idIngredient: Int = 0,
        @ColumnInfo(name = "amount")
        var amount: Int = 0,
        @ColumnInfo(name = "title")
        var title: String = "",
        @Ignore
        val time: Long = 0,
        @Ignore
        val portion: Int = 0
) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readLong(), parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(idRecipe)
        parcel.writeInt(idIngredient)
        parcel.writeInt(amount)
        parcel.writeString(title)
        parcel.writeLong(time)
        parcel.writeInt(portion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RecipeIngredients> = object : Parcelable.Creator<RecipeIngredients> {
            override fun createFromParcel(parcel: Parcel): RecipeIngredients {
                return RecipeIngredients(parcel)
            }

            override fun newArray(size: Int): Array<RecipeIngredients?> {
                return arrayOfNulls(size)
            }
        }
    }
}

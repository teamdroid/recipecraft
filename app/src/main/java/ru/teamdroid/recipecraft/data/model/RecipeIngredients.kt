package ru.teamdroid.recipecraft.data.model

import android.os.Parcel
import android.os.Parcelable

class RecipeIngredients(
        var id: Int = 0,
        var idRecipe: Int = 0,
        var idIngredient: Int = 0,
        var amount: Double = 0.0,
        var title: String = "",
        var time: Long = 0,
        var portion: Int = 0
) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readDouble(), parcel.readString()
            ?: "", parcel.readLong(), parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(idRecipe)
        parcel.writeInt(idIngredient)
        parcel.writeDouble(amount)
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

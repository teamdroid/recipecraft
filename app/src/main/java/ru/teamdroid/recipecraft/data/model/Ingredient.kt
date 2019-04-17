package ru.teamdroid.recipecraft.data.model

import android.os.Parcel
import android.os.Parcelable

class Ingredient(
        var idIngredient: Int = 0,
        var title: String = "",
        var amount: Double = 0.0,
        var id: Int = 0
) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString()
            ?: "", parcel.readDouble(), parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idIngredient)
        parcel.writeString(title)
        parcel.writeDouble(amount)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Ingredient> = object : Parcelable.Creator<Ingredient> {
            override fun createFromParcel(parcel: Parcel): Ingredient {
                return Ingredient(parcel)
            }

            override fun newArray(size: Int): Array<Ingredient?> {
                return arrayOfNulls(size)
            }
        }
    }
}

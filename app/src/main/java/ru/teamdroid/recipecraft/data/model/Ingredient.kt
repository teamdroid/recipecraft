package ru.teamdroid.recipecraft.data.model

import android.os.Parcel
import android.os.Parcelable

class Ingredient(
        var idIngredient: Int = 0,
        var title: String = "",
        var id: Int = 0,
        var amount: Double = 0.0,
        var idUnitMeasure: Int = 0,
        var title_unit_measure: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readDouble(), parcel.readInt(), parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idIngredient)
        parcel.writeString(title)
        parcel.writeInt(idUnitMeasure)
        parcel.writeString(title_unit_measure)
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

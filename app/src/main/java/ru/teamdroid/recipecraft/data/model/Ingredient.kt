package ru.teamdroid.recipecraft.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Ingredient(
        var idIngredient: Int = 0,
        var title: String = "",
        var id: Int = 0,
        var amount: Double = 0.0,
        var idUnitMeasure: Int = 0,
        @SerializedName("measureTitle")
        var measureTitle: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString() ?: "", parcel.readInt(), parcel.readDouble(), parcel.readInt(), parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idIngredient)
        parcel.writeString(title)
        parcel.writeInt(idUnitMeasure)
        parcel.writeString(measureTitle)
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

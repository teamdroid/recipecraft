package ru.teamdroid.recipecraft.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull

@Entity(tableName = "ingredients")
data class Ingredients(
        @PrimaryKey @NonNull @ColumnInfo(name = "idIngredient")
        var idIngredient: Int = 0,
        @ColumnInfo(name = "title")
        var title: String = "",
        @Ignore
        var amount: Int = 0,
        @Ignore
        var id: Int = 0
) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idIngredient)
        parcel.writeString(title)
        parcel.writeInt(amount)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Ingredients> = object : Parcelable.Creator<Ingredients> {
            override fun createFromParcel(parcel: Parcel): Ingredients {
                return Ingredients(parcel)
            }

            override fun newArray(size: Int): Array<Ingredients?> {
                return arrayOfNulls(size)
            }
        }
    }
}

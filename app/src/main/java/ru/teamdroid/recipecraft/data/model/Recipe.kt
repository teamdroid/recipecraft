package ru.teamdroid.recipecraft.data.model

import android.os.Parcel
import android.os.Parcelable

data class Recipe(
        var idRecipe: Int = 0,
        var title: String = "",
        var time: Long = 0,
        var portion: Int = 0,
        var isBookmarked: Boolean = false,
        var ingredients: MutableList<Ingredient> = arrayListOf(),
        var insctructions: MutableList<Instruction> = arrayListOf()
) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString(), parcel.readLong(), parcel.readInt(), parcel.readInt() == 0, mutableListOf<Ingredient>().apply {
        parcel.readArrayList(Ingredient::class.java.classLoader)
    })

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idRecipe)
        parcel.writeString(title)
        parcel.writeLong(time)
        parcel.writeInt(portion)
        parcel.writeInt(if (isBookmarked) 1 else 0)
        parcel.writeList(ingredients)
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
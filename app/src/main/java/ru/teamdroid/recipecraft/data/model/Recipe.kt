package ru.teamdroid.recipecraft.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Recipe(
        @SerializedName("id_recipe")
        var idRecipe: Int = 0,
        @SerializedName("title_ru")
        var title: String = "",
        var time: Long = 0,
        var portion: Int = 0,
        var type: String = "",
        var image: String = "",
        var isBookmarked: Boolean = false,
        @SerializedName("ingredients")
        var ingredients: MutableList<Ingredient> = arrayListOf(),
        @SerializedName("instructions")
        var instructions: MutableList<Instruction> = arrayListOf()
) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString()
            ?: "", parcel.readLong(),
            parcel.readInt(), parcel.readString() ?: "", parcel.readString()
            ?: "", parcel.readInt() == 0,
            mutableListOf<Ingredient>().apply { parcel.readArrayList(Ingredient::class.java.classLoader) },
            mutableListOf<Instruction>().apply { parcel.readArrayList(Instruction::class.java.classLoader) }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idRecipe)
        parcel.writeString(title)
        parcel.writeLong(time)
        parcel.writeInt(portion)
        parcel.writeString(type)
        parcel.writeString(image)
        parcel.writeInt(if (isBookmarked) 1 else 0)
        parcel.writeList(ingredients)
        parcel.writeList(instructions)
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
package ru.teamdroid.recipecraft.concept.data.model

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull

@Entity(tableName = "Recipes")
data class Recipes(
        @PrimaryKey @NonNull @ColumnInfo(name = "idRecipe")
        var idRecipe: Int = 0,
        @ColumnInfo(name = "title")
        var title: String = "",
        @ColumnInfo(name = "time")
        var time: Long = 0,
        @ColumnInfo(name = "portion")
        var portion: Int = 0,
        @ColumnInfo(name = "isBookmarked")
        var isBookmarked: Boolean = false,
        @Ignore
        var ingredients: MutableList<Ingredients> = arrayListOf(),
        @Ignore
        var insctructions: MutableList<Instruction> = arrayListOf()
) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString(), parcel.readLong(), parcel.readInt(), parcel.readInt() == 0, mutableListOf<Ingredients>().apply {
        parcel.readArrayList(Ingredients::class.java.classLoader)
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
        val CREATOR: Parcelable.Creator<Recipes> = object : Parcelable.Creator<Recipes> {
            override fun createFromParcel(parcel: Parcel): Recipes {
                return Recipes(parcel)
            }

            override fun newArray(size: Int): Array<Recipes?> {
                return arrayOfNulls(size)
            }
        }
    }
}



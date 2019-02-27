package ru.teamdroid.recipecraft.room.entity

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull

@Entity(tableName = "Recipe")
data class Recipe(
        @PrimaryKey @NonNull @ColumnInfo(name = "id")
        var id: Int = 0,
        @ColumnInfo(name = "title")
        var title: String = "",
        @ColumnInfo(name = "isBookmarked")
        var isBookmarked: Boolean = false,
        @Ignore
        var ingredients: MutableList<Ingredients> = ArrayList()
) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString(), parcel.readInt() == 0, mutableListOf<Ingredients>().apply {
        parcel.readArrayList(Ingredients::class.java.classLoader)
    })

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
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



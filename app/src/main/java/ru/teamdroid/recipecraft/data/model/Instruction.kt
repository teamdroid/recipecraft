package ru.teamdroid.recipecraft.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Instruction(
        @SerializedName("id_instruction")
        var idInstruction: Int = 0,
        var idRecipe: Int = 0,
        @SerializedName("title_ru")
        var title: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt(), parcel.readString()
            ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idInstruction)
        parcel.writeInt(idRecipe)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Instruction> = object : Parcelable.Creator<Instruction> {
            override fun createFromParcel(parcel: Parcel): Instruction {
                return Instruction(parcel)
            }

            override fun newArray(size: Int): Array<Instruction?> {
                return arrayOfNulls(size)
            }
        }
    }
}

package com.example.navigation.ui.problem

import android.os.Parcel
import android.os.Parcelable

data class Problem(
    val question: String,
    val options: List<String>,
    val answerIndex: Int,
    var selectedOption: Int = -1,
    var isCorrect: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: listOf(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)
        parcel.writeStringList(options)
        parcel.writeInt(answerIndex)
        parcel.writeInt(selectedOption)
        parcel.writeByte(if (isCorrect) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Problem> {
        override fun createFromParcel(parcel: Parcel): Problem {
            return Problem(parcel)
        }

        override fun newArray(size: Int): Array<Problem?> {
            return arrayOfNulls(size)
        }
    }
}

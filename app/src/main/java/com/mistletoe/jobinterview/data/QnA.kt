package com.mistletoe.jobinterview.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qna")
data class QnA(
    @PrimaryKey(autoGenerate = true) val qnaId: Int = 0,
    val tag: String = "",
    val question: String = "",
    val answer: String = "",
    val isBookmarked: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(qnaId)
        parcel.writeString(tag)
        parcel.writeString(question)
        parcel.writeString(answer)
        parcel.writeByte(if (isBookmarked) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<QnA> {
        override fun createFromParcel(parcel: Parcel): QnA {
            return QnA(parcel)
        }

        override fun newArray(size: Int): Array<QnA?> {
            return arrayOfNulls(size)
        }
    }
}

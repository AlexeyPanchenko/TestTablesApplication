package ru.aol_panchenko.tables.presentation.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by alexey on 28.08.17.
 */
class Score() : Parcelable {
    var uId: String? = null
    var value: Int = 0
    var timeStamp: Long = 0

    constructor(uId: String, value: Int, timeStamp: Long) : this(){
        this.uId = uId
        this.value = value
        this.timeStamp = timeStamp
    }

    constructor(parcel: Parcel) : this() {
        uId = parcel.readString()
        value = parcel.readInt()
        timeStamp = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uId)
        parcel.writeInt(value)
        parcel.writeLong(timeStamp)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Score> {
        override fun createFromParcel(parcel: Parcel): Score = Score(parcel)

        override fun newArray(size: Int): Array<Score?> = arrayOfNulls(size)
    }
}
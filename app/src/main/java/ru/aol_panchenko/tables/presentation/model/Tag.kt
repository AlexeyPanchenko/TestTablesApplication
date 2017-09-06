package ru.aol_panchenko.tables.presentation.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by alexey on 28.08.17.
 */
class Tag() : Parcelable {
    var uId: String? = null
    var value: String? = null
    var timeStamp: Long = 0

    constructor(uId: String, value: String, timeStamp: Long): this() {
        this.uId = uId
        this.value = value
        this.timeStamp = timeStamp
    }

    constructor(parcel: Parcel) : this() {
        uId = parcel.readString()
        value = parcel.readString()
        timeStamp = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uId)
        parcel.writeString(value)
        parcel.writeLong(timeStamp)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Tag> {
        override fun createFromParcel(parcel: Parcel): Tag = Tag(parcel)

        override fun newArray(size: Int): Array<Tag?> = arrayOfNulls(size)
    }
}
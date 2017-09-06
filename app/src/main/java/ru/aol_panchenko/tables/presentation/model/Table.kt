package ru.aol_panchenko.tables.presentation.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by alexey on 28.08.17.
 */
class Table() : Parcelable {
    var uId: String? = null
    var tableId: String? = null
    var scores: ArrayList<Score>? = null
    var tags: ArrayList<Tag>? = null
    var holders: ArrayList<String>? = null

    constructor(parcel: Parcel) : this() {
        uId = parcel.readString()
        tableId = parcel.readString()
        scores = parcel.createTypedArrayList(Score)
        tags = parcel.createTypedArrayList(Tag)
        holders = parcel.createStringArrayList()
    }

    constructor(uId: String, tableId: String, scores: ArrayList<Score>, tags: ArrayList<Tag>, holders: ArrayList<String>): this() {
        this.uId = uId
        this.tableId = tableId
        this.scores = scores
        this.tags = tags
        this.holders = holders
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uId)
        parcel.writeString(tableId)
        parcel.writeTypedList(scores)
        parcel.writeTypedList(tags)
        parcel.writeStringList(holders)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Table> {
        override fun createFromParcel(parcel: Parcel): Table = Table(parcel)

        override fun newArray(size: Int): Array<Table?> = arrayOfNulls(size)
    }


}